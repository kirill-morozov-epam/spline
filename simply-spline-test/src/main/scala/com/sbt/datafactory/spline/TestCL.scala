/*
 * Copyright 2017 ABSA Group Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sbt.datafactory.spline

import org.apache.spark.sql.{SQLContext, SQLImplicits, SaveMode, SparkSession}
import za.co.absa.spline.harvester.SparkLineageInitializer._

object TestCL  extends SQLImplicits with App {

  private val sparkBuilder = SparkSession.builder()

  sparkBuilder.appName("simply-spline-test")
  sparkBuilder.master("local[*]")

  val conf: Seq[(String, String)] = Nil

  for ((k, v) <- conf) sparkBuilder.config(k, v)

  val spark: SparkSession = sparkBuilder.getOrCreate()

  spark.enableLineageTracking()

  val ddf = spark.read.option("header", "true").option("inferSchema", "true").csv("data/output/batch/java-sample.csv")
  ddf.write.mode(SaveMode.Overwrite).csv("data/output/batch/java-sample1.csv")

  protected override def _sqlContext: SQLContext = spark.sqlContext

}
