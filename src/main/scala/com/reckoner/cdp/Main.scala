package com.reckoner.cdp
import com.reckoner.cdp.HiveClient
import org.apache.logging.log4j.scala.Logging

object Main extends Logging {
  case class CommandLineArgs(
    host: String = "127.0.0.1",
    port: String = "10000",
    database: String = "tmp",
    user: String = "hive",
    password: String = "hive",
    query: String = "show tables"
  )

  def main(args: Array[String]) :Unit = {
    val parser = new scopt.OptionParser[CommandLineArgs]("reckoner-cdp-parser") {
      head("scopt", "3.6.0")

      opt[String]('h', "host") action { (x, c) =>
        c.copy(host = x)
      } text ("hive host")

      opt[String]('p', "port") action { (x, c) =>
        c.copy(port = x)
      } text ("hive port")

      opt[String]('d', "database") action { (x, c) =>
        c.copy(database = x)
      } text ("database")

      opt[String]('u', "user") action { (x, c) =>
        c.copy(user = x)
      } text ("user")

      opt[String]('w', "password") action { (x, c) => 
        c.copy(password = x)
      } text ("password")

      opt[String]('q', "query") action { (x, c) => 
        c.copy(query = x)
      } text ("query")
    }

    parser.parse(args, CommandLineArgs()) match {
      case Some(opts) => run(opts)
      case None => println("option parse error")
    }
  }

  def run(opts: CommandLineArgs): Unit = {
    logger.info("-------- execute hive query ---------")
    logger.info(opts.query)
    val hiveConn = new HiveClient(opts.host, opts.port, opts.database, opts.user, opts.password)
    hiveConn.createConn()
    hiveConn.exec(opts.query)
    logger.info("-------------- done ! ---------------")
  }
}
