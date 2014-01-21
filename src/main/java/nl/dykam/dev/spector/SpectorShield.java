package nl.dykam.dev.spector;

public class SpectorShield {
    private boolean canInteract = false;
    private boolean canChat = true;
    private boolean canPickup = false;
    private boolean canDrop = false;
    private boolean targetable = true;
    private boolean invincible = true;
    private boolean peaceful = true;

    public boolean canInteract() {
        return canInteract;
    }
    public SpectorShield canInteract(boolean canInteract) {
        this.canInteract = canInteract;
        return this;
    }

    public boolean canChat() {
        return canChat;
    }
    public SpectorShield canChat(boolean canChat) {
        this.canChat = canChat;
        return this;
    }

    public boolean canPickup() {
        return canPickup;
    }
    public SpectorShield canPickup(boolean canPickup) {
        this.canPickup = canPickup;
        return this;
    }

    public boolean canDrop() {
        return canDrop;
    }
    public SpectorShield canDrop(boolean canDrop) {
        this.canDrop = canDrop;
        return this;
    }

    public boolean isTargetable() {
        return targetable;
    }
    public SpectorShield setTargetable(boolean canFollow) {
        this.targetable = canFollow;
        return this;
    }

    public boolean isInvincible() {
        return invincible;
    }
    public SpectorShield setInvincible(boolean invincible) {
        this.invincible = invincible;
        return this;
    }

    public boolean isPeaceful() {
        return peaceful;
    }
    public SpectorShield setPeaceful(boolean peaceful) {
        this.peaceful = peaceful;
        return this;
    }

    public static SpectorShield ghost() {
        return ghost(new SpectorShield());
    }

    public static SpectorShield ghost(SpectorShield spectorShield) {
        return spectorShield
                .setInvincible(true)
                .setPeaceful(true)
                .setTargetable(false)
                .canPickup(false)
                .canDrop(false)
                .canInteract(false)
                .canChat(false);
    }

    public static SpectorShield noShield() {
        return noShield(new SpectorShield());
    }

    public static SpectorShield noShield(SpectorShield spectorShield) {
        return spectorShield
                .setInvincible(false)
                .setPeaceful(false)
                .setTargetable(true)
                .canPickup(true)
                .canDrop(true)
                .canInteract(true)
                .canChat(true);
    }
}
