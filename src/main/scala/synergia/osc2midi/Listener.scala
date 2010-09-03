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
		val command = args(0).toString
		val address = message.getAddress
		
		address match {
			case "/platri" => command match {
				case "midi" => {
					// OSC Message: /platri midi [command] [channel] [data1] [data2]
					val command = args(1).asInstanceOf[Int]
					val channel = args(2).asInstanceOf[Int]
					val data1   = args(3).asInstanceOf[Int]
					val data2   = args(4).asInstanceOf[Int]
					
					if(args.length == 5){
						try {
							Midi << Note.raw(command, if(channel == -1) 9 else channel, data1, if(data2 == -1) 100 else data2)
						} catch {
							case _ => 
								println("[osc2midi] javax.sound.midi exception")
								println("[osc2midi] Wrong message: " + message)
						}
					} else {
						println("[osc2midi] Wrong message: " + message)
					}
				}
			}
		}
	}
}
