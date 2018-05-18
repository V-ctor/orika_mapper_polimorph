import com.victor.*
import com.victor.Target
import ma.glasnost.orika.CustomMapper
import ma.glasnost.orika.MapperFacade
import ma.glasnost.orika.MapperFactory
import ma.glasnost.orika.impl.DefaultMapperFactory
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.assertEquals
import ma.glasnost.orika.MappingContext
import ma.glasnost.orika.impl.ConfigurableMapper
import ma.glasnost.orika.CustomConverter
import java.lang.reflect.Type


internal class TestKt {
    @Test
    fun test() {
        val mapperFactory = DefaultMapperFactory.Builder().build()

        mapperFactory.classMap<Derived1, TargetKt>(Derived1::class.java, TargetKt::class.java)
//                .customize(object : CustomMapper<Derived1, TargetKt>() {
//                    override fun mapAtoB(a: Derived1?, b: TargetKt?, context: MappingContext?) {
//                        b!!.data
//                    }
//                })
                .field("message", "info")
                .byDefault()
                .register()
        mapperFactory.classMap<Derived2, TargetKt>(Derived2::class.java, TargetKt::class.java)
                .field("detail", "info")
                .byDefault()
                .register()
        val mapper = mapperFactory.mapperFacade

        //        Target target /*= new Target(info)*/;
//         {
            val derived1 = Derived1("")

            val target = mapper.map<Base, TargetKt>(derived1, TargetKt::class.java)
            assertEquals(target.info, derived1.message)
//        }
//         {
            val derived2 = Derived2()

            val target1 = mapper.map<Base, TargetKt>(derived2, TargetKt::class.java)
            assertEquals(target1.info, derived2.detail)
//        }

    }
}



//
//class MyConverter : CustomConverter<Derived1, TargetKt>() {
//    fun convert(source: Derived1, destinationType: Type<out TargetKt>): TargetKt {
//        // return a new instance of destinationType with all properties filled
//    }
//}