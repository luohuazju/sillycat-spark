package com.sillycat.spark.model

import org.apache.spark.serializer.KryoRegistrator
import com.esotericsoftware.kryo.Kryo
import org.joda.time.DateTime

class CustomRegistrator extends KryoRegistrator{

  override def registerClasses(kryo:Kryo): Unit ={
    kryo.register(classOf[Product])
    kryo.register(classOf[DateTime])
  }

}
