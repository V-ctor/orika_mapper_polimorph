import com.victor.Base;
import com.victor.Derived1;
import com.victor.Derived2;
import com.victor.Target;
import ma.glasnost.orika.*;
import ma.glasnost.orika.converter.ConverterFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.metadata.Type;
import ma.glasnost.orika.metadata.TypeFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Test1 {
    @Test
    public void test() {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();

        ConverterFactory converterFactory = mapperFactory.getConverterFactory();
        converterFactory.registerConverter(new MyConverter());
        mapperFactory.classMap(Derived1.class, Target.class)
//            .fieldMap("detail").exclude().add()
//            .field("message", "info")
            .byDefault()
            .register();

        Type<Derived1> orikaAType = TypeFactory.valueOf(Derived1.class);
        Type<Target> orikaBType = TypeFactory.valueOf(Target.class);

//        ObjectFactory<Derived1> objectFactoryDerived1 = (Object o, MappingContext mappingContext) -> {
//            Target source = (Target) o;
//            return new Derived1(source.getInfo());
//        };

        ObjectFactory<Target> objectFactoryOrikaB = (Object o, MappingContext mappingContext) -> {
            Derived1 source = (Derived1) o;
            return new Target(source.getMessage(),"");
        };

//        mapperFactory.registerObjectFactory(objectFactoryDerived1, orikaAType, orikaBType);
        mapperFactory.registerObjectFactory(objectFactoryOrikaB, orikaBType, orikaAType);




        mapperFactory.classMap(Derived2.class, Target.class)
            .field("detail", "info")
            .byDefault()
            .register();
        MapperFacade mapper = mapperFactory.getMapperFacade();

//        Target target /*= new Target(info)*/;
        {
            Base derived1 = new Derived1("la la");

            Target target = mapper.map(derived1, Target.class);
            assertEquals(target.getInfo(), ((Derived1) derived1).getMessage());
        }
//        {
//            Base derived2 = new Derived2();
//
//            Target target = mapper.map(derived2, Target.class);
//            assertEquals(target.getInfo(), ((Derived2) derived2).detail);
//        }

    }


}

class MyConverter extends CustomConverter<Derived1, Target> {

    public Target convert(Derived1 derived1, Type<? extends Target> type, MappingContext mappingContext) {
        return new Target("", derived1.getMessage());
    }
}
