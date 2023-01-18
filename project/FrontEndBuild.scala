import play.sbt.PlayRunHook
import sbt._
import scala.sys.process._

object FrontEndBuild {
  def apply(base: File): PlayRunHook = {

    object BuildProcess extends PlayRunHook {

      override def beforeStarted(): Unit = {
        val shell: Seq[String] = if (sys.props("os.name").contains("Windows")) Seq("cmd", "/c") else Seq("bash", "-c")
        val npmInstall: Seq[String] = shell :+ "npm install"
        val npmBuild: Seq[String] = shell :+   "npm run build"
        print("building frontend...")
        if((Process(npmInstall, base) #&& Process(npmBuild, base) !) == 0) {
            printf("frontend build successful!")
        } else {
            throw new IllegalStateException("frontend build failed!")
        }
      }
    }

    BuildProcess
  }
}