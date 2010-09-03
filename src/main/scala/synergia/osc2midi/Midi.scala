package synergia.osc2midi

import javax.sound.midi._

object Note {
	def on(data1: Int, data2: Int) = msg(ShortMessage.NOTE_ON, data1, data2)
	
	def off(data1: Int, data2: Int) = msg(ShortMessage.NOTE_OFF, data1, data2)
	
	def msg(kind: Int, data1: Int, data2: Int) = raw(kind, 9, data1, data2)
	
	def raw(kind: Int, channel: Int, data1: Int, data2: Int) = {
		val m = new ShortMessage
		m.setMessage(kind, channel, data1, data2)
		m
	}
}

object Midi {
	var device: Option[MidiDevice] = None
	var receiver: Option[Receiver] = None
	
	def <<(msg: MidiMessage) = receiver.foreach(_.send(msg, -1))
	
	def start(deviceName: String) {
		val devices = MidiSystem.getMidiDeviceInfo.toList
		devices.find(_.getName == deviceName).map(MidiSystem.getMidiDevice(_)) match {
			case Some(d) => {
				d.open
				receiver = if(d.getReceiver != null) Some(d.getReceiver) else None
			}
			case None => {
				println("Could not find device with name: " + deviceName)
				println("Available devices:")
				devices.foreach(e => println(" * " + e.getName))	
				exit(0)			
			}
		}
		
		// set drums!
		val msg = new ShortMessage
		msg.setMessage(ShortMessage.PROGRAM_CHANGE, 9, 1, 0)
		this << msg
	}
	
	def stop {
		receiver foreach (_.close)
		device foreach (_.close)
	}
	
}