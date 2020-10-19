package abstract_factory;

import factory.paddle.ColoredPaddleFactory;
import factory.paddle.PaddleFactory;

public class PaddleFactoryProducer {
    public static AbstractPaddleFactory getFactory(PaddleFactoryType factoryType) {
        if(factoryType == PaddleFactoryType.NORMAL){
            return PaddleFactory.getInstance();
        } else {
            return ColoredPaddleFactory.getInstance();
        }
    }
}
