package template;

public final class DashedPaddleTemplate extends PaddleTemplate {

    @Override
    public boolean needsColor() {
        return false;
    }

    @Override
    public boolean needsDashes() {
        return true;
    }

    @Override
    public boolean needsAngle() {
        return false;
    }
}
