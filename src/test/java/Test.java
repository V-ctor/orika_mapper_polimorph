import com.victor.Base;
import com.victor.Derived1;
import com.victor.Derived2;
import com.victor.Target;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Test1 {
    @Test
    public void test() {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();

        mapperFactory.classMap(Derived1.class, Target.class)
            .field("message", "info")
            .byDefault()
            .register();
        mapperFactory.classMap(Derived2.class, Target.class)
            .field("detail", "info")
            .byDefault()
            .register();
        MapperFacade mapper = mapperFactory.getMapperFacade();

        Target target = new Target();
        {
            Base derived1 = new Derived1();

            mapper.map(derived1, target);
            assertEquals(target.info, ((Derived1) derived1).message);
        }
        {
            Base derived2 = new Derived2();

            mapper.map(derived2, target);
            assertEquals(target.info, ((Derived2) derived2).detail);
        }

    }
}







