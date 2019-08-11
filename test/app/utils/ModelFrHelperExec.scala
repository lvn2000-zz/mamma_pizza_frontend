//package app.utils
//
//import org.scalatest.FlatSpec
//
//import app.TestInit
//import utils.ModelFrHelper
//
//class ModelFrHelperExec extends FlatSpec with TestInit {
//
//  val modelHelper = ModelFrHelper.apply
//
//  "ModelFrHelper " should
//    "return converted data PizzaCatalogFrItem to PizzaCatalogItem" in {
//
//      val result = catalogFixture.flatMap(modelHelper.convert(_)).map(_.asInstanceOf[PizzaCatalogItem])
//      assert(result.nonEmpty)
//    }
//
//  "ModelFrHelper " should
//    "return converted data PizzaCatalogItem to PizzaCatalogFrItem" in {
//
//      val src = catalogFixture.flatMap(modelHelper.convert(_).map(_.asInstanceOf[PizzaCatalogItem]))
//      val result = src.map(modelHelper.convert(_)).flatten.map(_.asInstanceOf[PizzaCatalogFrItem])
//      assert(result.nonEmpty)
//    }
//
//  "ModelFrHelper " should
//    "return converted data SimpleCatalogFrItem to SimpleCatalogItem" in {
//
//      val result = simpleCatalogFixture.flatMap(modelHelper.convert(_)).map(_.asInstanceOf[SimpleCatalogItem])
//      assert(result.nonEmpty)
//    }
//
//  "ModelFrHelper " should
//    "return converted data SimpleCatalogItem to SimpleCatalogFrItem" in {
//
//      val src = simpleCatalogFixture.flatMap(modelHelper.convert(_)).map(_.asInstanceOf[SimpleCatalogItem])
//      val result = src.map(modelHelper.convert(_).map(_.asInstanceOf[SimpleCatalogFrItem]))
//      assert(result.nonEmpty)
//    }
//
//}
