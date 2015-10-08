/*
** Copyright [2013-2015] [Megam Systems]
**
** Licensed under the Apache License, Version 2.0 (the "License");
** you may not use this file except in compliance with the License.
** You may obtain a copy of the License at
**
** http://www.apache.org/licenses/LICENSE-2.0
**
** Unless required by applicable law or agreed to in writing, software
** distributed under the License is distributed on an "AS IS" BASIS,
** WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
** See the License for the specific language governing permissions and
** limitations under the License.
*/
package test.billing

import scalaz._
import scalaz.syntax.SemigroupOps
import scalaz.NonEmptyList._
import scalaz.Validation._
import java.net.URL
import com.stackmob.newman.response.{ HttpResponse, HttpResponseCode }
import com.stackmob.newman._
import com.stackmob.newman.{HttpClient}
import com.stackmob.newman.dsl._
import org.specs2._
import org.specs2.Specification
import org.specs2.matcher.MatchResult
import org.specs2.execute.{ Result => SpecsResult }
import models.billing._
import models.billing.Billedhistories
import controllers.stack.HeaderConstants._
import test.{ Context }

 //* @author rajthilak
 //*

class BilledhistoriesSpec extends Specification {

   def is =
    "BilledhistoriesSpec".title ^
      """
  BilledhistoriesSpec is the implementation that calls the megam_play API server with the /billedhistories url
  """ ^
      p ^
  "The Client Should" ^ br ^
    "Correctly do POST  requests with an valid datas" ! create.succeeds ^ p ^    
    end 


    case object create extends Context {

    protected override def urlSuffix: String = "billedhistories/content"

    protected override def bodyToStick: Option[String] = {
      val contentToEncode = "{" +
        "\"accounts_id\": \"565656\"," +
        "\"assembly_id\":\"456436\"," +
        "\"bill_type\": \"paypal\"," +
        "\"billing_amount\":\"2000\"," +
        "\"currency_type\":\"USD\"" +
        "}"

      Some(new String(contentToEncode))
    }
    protected override def headersOpt: Option[Map[String, String]] = None
    
    private val post = POST(url)(httpClient)
      .addHeaders(headers)
      .addBody(body)

    def succeeds: SpecsResult = {   
      val resp = execute(post)
      resp.code must beTheSameResponseCodeAs(HttpResponseCode.Created)
    }
  }

}
