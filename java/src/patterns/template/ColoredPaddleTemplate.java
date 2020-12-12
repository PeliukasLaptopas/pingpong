package patterns.template;

public final class ColoredPaddleTemplate extends PaddleTemplate {

    @Override
    public boolean needsColor() {
        return true;
    }

    @Override
    public boolean needsDashes() {
        return false;
    }

    @Override
    public boolean needsAngle() {
        return false;
    }
}
