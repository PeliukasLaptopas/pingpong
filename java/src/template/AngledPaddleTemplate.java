package template;

public final class AngledPaddleTemplate extends PaddleTemplate {

    @Override
    public boolean needsColor() {
        return false;
    }

    @Override
    public boolean needsDashes() {
        return false;
    }

    @Override
    public boolean needsAngle() {
        return true;
    }
}
