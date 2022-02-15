package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.libs.ws._
import dao.ResortData
import models._
import scrapers.ABasinScraper
import collection.mutable.ArrayBuffer
import collection.mutable.Map
import scala.concurrent.Future
import scala.concurrent._
import scala.concurrent.duration.{SECONDS, Duration}
import ExecutionContext.Implicits.global 
import scrapers.ScraperFactory


/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(val controllerComponents: ControllerComponents, val resortData: ResortData) extends BaseController {

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index() = Action { implicit request: Request[AnyContent] =>


    Ok(views.html.index())
  }

  def scrape() = Action { implicit request: Request[AnyContent] => 
    var resortDataMap: Map[Resorts, DatabaseSnapshot] = Map[Resorts, DatabaseSnapshot]()
    var resortFutureSeq: ArrayBuffer[Future[Unit]] = ArrayBuffer.empty
    
    resortFutureSeq.addOne(generateFuture(resortDataMap, ArapahoeBasin))
    
    Await.ready(Future.sequence(resortFutureSeq), Duration.create(10, SECONDS))
    resortData.setSnapshotForResort(resortDataMap.toMap)
    Ok
  }

  private def generateFuture(resortDataMap: Map[Resorts, DatabaseSnapshot], resort: Resorts): Future[Unit] = {
    Future {
      try {
        val scraper = ScraperFactory.initializeScraper(resort)
        resortDataMap.put(resort, scraper.scrapeResort())
      } catch { case e: Exception => println(e.printStackTrace()) }
    }
  }
}
