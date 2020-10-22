package paddles.abstractfactory;

import paddles.factory.ColoredPaddleFactory;
import paddles.factory.PaddleFactory;

public class PaddleFactoryProducer {
    public static AbstractPaddleFactory getFactory(PaddleFactoryType factoryType) {
        if(factoryType == PaddleFactoryType.NORMAL){
            return PaddleFactory.getInstance();
        } else {
            return ColoredPaddleFactory.getInstance();
        }
    }
}
