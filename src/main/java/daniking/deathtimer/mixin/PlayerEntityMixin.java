package daniking.deathtimer.mixin;

import daniking.deathtimer.Timer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin implements Timer  {

    private static final String TIMER_KEY = "Timer";
    private int timer;
    private int ticks = 0;

    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(CallbackInfo info) {
        ticks++;
        if (ticks >= 20) { // 20 ticks = 1 second
            timer++;
            ticks = 0;
        }
    }
    @Inject(method = "writeCustomDataToNbt", at = @At("RETURN"))
    public void save(NbtCompound nbt, CallbackInfo info) {
        nbt.putInt(TIMER_KEY, this.timer);
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("RETURN"))
    public void write(NbtCompound nbt, CallbackInfo info) {
        this.timer = nbt.getInt(TIMER_KEY);
    }

    @Override
    public int getTime() {
        return this.timer;
    }
}
