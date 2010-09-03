package synergia.osc2midi

object App {
	def main(args: Array[String]) {
		val deviceName = if(args.length >= 1) args(0) else "Java Sound Synthesizer"
		val port = if(args.length >= 2) args(1).toInt else 4444
		Midi.start(deviceName)
		val listener = new Listener(port)
		listener.connect
	}
}
