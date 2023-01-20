package scrapers

import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL.Parse._
import net.ruippeixotog.scalascraper.model.Element

import models._

class EpicScraper(resort: Resorts) extends BaseScraper(resort)  {

    val descriptionClass = ".snow_report__metrics__description"
    val meaurementClass = ".snow_report__metrics__measurement"
    val scripts = browser.get(resort.scrapeUrl) >> elementList("script")
    val snowReportScript = scripts.filter(el => el.innerHtml contains "TwentyFourHourSnowfall") match {
        case script if script.isEmpty => ""
        case script => script(0).innerHtml
    }

    protected def scrape24HrSnowFall(): Int = {
        val extract24HrSnowFall = raw"\"TwentyFourHourSnowfall\":\{\"Inches\":\"(.*?)\"".r
        val snowFall = for (m <- extract24HrSnowFall.findFirstMatchIn(snowReportScript)) yield m.group(1)
        snowFall.getOrElse("").toIntOption.getOrElse(0)
    }
    
    protected def scrapeBaseDepth(): Int = {
        val extractBaseDepth = raw"\"BaseDepth\":\{\"Inches\":\"(.*?)\"".r
        val baseDepth = for (m <- extractBaseDepth.findFirstMatchIn(snowReportScript)) yield m.group(1)
        baseDepth.getOrElse("").toIntOption.getOrElse(0)
    }
}
