package synergia.osc2midi

import com.illposed.osc._
import java.util.Date

class Listener(val port: Int) extends OSCListener {
	val osc = new OSCPortIn(port)
	
	def connect {
		try {
			osc.addListener("/platri", this)
			osc.startListening
			println("Connected on port " + port)
		} catch {
			case _ => println("Failed to connect on port " + port)
		}
	}
	
	def disconnect { osc.stopListening }
	
	def acceptMessage(date: Date, message: OSCMessage) {
		val args = message.getArguments
		println(args.toList)
		val command = args(0).toString
		val address = message.getAddress
		
		address match {
			case "/platri" => command match {
				case "midi" => {
					//val a = args(1).asInstanceOf[Int]
					//println("a: " + a)
				}
			}
		}
	}
}
