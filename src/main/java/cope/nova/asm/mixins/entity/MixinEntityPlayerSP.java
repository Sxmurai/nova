package cope.nova.asm.mixins.entity;

import com.mojang.authlib.GameProfile;
import cope.nova.client.events.entity.InputUpdateEvent;
import cope.nova.client.events.entity.UpdateWalkingPlayerEvent;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.util.MovementInput;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityPlayerSP.class)
public abstract class MixinEntityPlayerSP extends AbstractClientPlayer {
    @Shadow @Final public NetHandlerPlayClient sendQueue;
    @Shadow private boolean serverSprintState;
    @Shadow private boolean serverSneakState;
    @Shadow private double lastReportedPosX;
    @Shadow private double lastReportedPosY;
    @Shadow private double lastReportedPosZ;
    @Shadow private float lastReportedYaw;
    @Shadow private float lastReportedPitch;
    @Shadow private int positionUpdateTicks;
    @Shadow public MovementInput movementInput;

    public MixinEntityPlayerSP(World worldIn, GameProfile playerProfile) {
        super(worldIn, playerProfile);
    }

    @Shadow public abstract boolean isCurrentViewEntity();

    // this is so fucking scuffed, why the hell is there no InputUpdateEvent like 1.12.2 has forge smh
    // if you dont understand this injection, look at where they check if the player is using an item in the decompiled class file
    @Inject(method = "onLivingUpdate", at = @At(value = "FIELD", target = "Lnet/minecraft/client/entity/EntityPlayerSP;sprintToggleTimer:I", shift = At.Shift.AFTER))
    public void onLivingUpdate(CallbackInfo info) {
        MinecraftForge.EVENT_BUS.post(new InputUpdateEvent(this.movementInput));
    }

    @Inject(method = "onUpdateWalkingPlayer", at = @At("HEAD"), cancellable = true)
    public void onUpdateWalkingPlayerPre(CallbackInfo info) {
        UpdateWalkingPlayerEvent event = new UpdateWalkingPlayerEvent(UpdateWalkingPlayerEvent.Era.Pre, this.rotationYaw, this.rotationPitch);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.isCanceled()) {
            this.updatePositioning(event.getYaw(), event.getPitch());
        }
    }

    @Inject(method = "onUpdateWalkingPlayer", at = @At("RETURN"))
    public void onUpdateWalkingPlayerPost(CallbackInfo info) {
        MinecraftForge.EVENT_BUS.post(new UpdateWalkingPlayerEvent(UpdateWalkingPlayerEvent.Era.Post));
    }

    // taken straight from minecraft. only difference is that we modify the rotations we need this so the server thinks we're rotated to where we're actually not
    private void updatePositioning(float yaw, float pitch) {
        if (this.isSprinting() != this.serverSprintState) {
            this.sendQueue.addToSendQueue(new C0BPacketEntityAction(this, this.isSneaking() ? C0BPacketEntityAction.Action.START_SPRINTING : C0BPacketEntityAction.Action.STOP_SPRINTING));
            this.serverSprintState = this.isSprinting();
        }

        if (this.isSneaking() != this.serverSneakState) {
            this.sendQueue.addToSendQueue(new C0BPacketEntityAction(this, this.isSneaking() ? C0BPacketEntityAction.Action.START_SNEAKING : C0BPacketEntityAction.Action.STOP_SNEAKING));
            this.serverSneakState = this.isSneaking();
        }

        double minY = this.getEntityBoundingBox().minY;

        if (this.isCurrentViewEntity()) {
            boolean moved = Math.pow(this.posX - this.lastReportedPosX, 2) + Math.pow(this.posY - this.lastReportedPosY, 2) + Math.pow(this.posZ - this.lastReportedPosZ, 2) > 9.0E-4D || this.positionUpdateTicks >= 20;
            boolean rotated = yaw - this.lastReportedYaw != 0.0 || pitch - this.lastReportedPitch != 0.0;

            if (this.ridingEntity == null) {
                if (moved && rotated) {
                    this.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(this.posX, minY, this.posZ, yaw, pitch, this.onGround));
                } else if (moved) {
                    this.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.posX, minY, this.posZ, this.onGround));
                } else if (rotated) {
                    this.sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(yaw, pitch, this.onGround));
                } else {
                    this.sendQueue.addToSendQueue(new C03PacketPlayer(this.onGround));
                }
            } else {
                this.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(this.motionX, -999.0D, this.motionZ, yaw, pitch, this.onGround));
                moved = false;
            }

            ++this.positionUpdateTicks;

            if (moved) {
                this.lastReportedPosX = this.posX;
                this.lastReportedPosY = minY;
                this.lastReportedPosZ = this.posZ;
                this.positionUpdateTicks = 0;
            }

            if (rotated) {
                this.lastReportedYaw = yaw;
                this.lastReportedPitch = pitch;
            }
        }
    }
}
