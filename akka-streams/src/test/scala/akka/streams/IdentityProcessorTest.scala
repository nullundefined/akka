package akka.streams

import org.scalatest.testng.TestNGSuiteLike
import asyncrx.spi.Publisher
import asyncrx.api.Processor
import asyncrx.tck.IdentityProcessorVerification
import akka.streams.Operation._

class IdentityProcessorTest extends IdentityProcessorVerification[Int] with WithActorSystem with TestNGSuiteLike {

  def createIdentityProcessor(maxBufferSize: Int): Processor[Int, Int] = {
    val settings = ActorBasedImplementationSettings(system, maxFanOutBufferSize = maxBufferSize)
    implicit val abif = new ActorBasedImplementationFactory(settings)
    abif.toProcessor(Identity[Int]())
  }

  def createHelperPublisher(elements: Int): Publisher[Int] = {
    import system.dispatcher
    val iter = Iterator from 1000
    TestProducer(if (elements > 0) iter take elements else iter).getPublisher
  }
}
