package synergia.osc2midi

object App {
	def main(args: Array[String]) {
		val port = if(args.length == 1) args(1).toInt else 4444
		val listener = new Listener(port)
		listener.connect
	}
}
