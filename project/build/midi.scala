import sbt._

class MidiProject(info: ProjectInfo) extends DefaultProject(info) with AssemblyProject {
	override def mainClass = Some("synergia.osc2midi.App")
}
