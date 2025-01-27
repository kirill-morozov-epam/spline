/*
 * Copyright 2019 ABSA Group Limited
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
export class Property {
    public type: string
    public value: any
    public metadata: any

    constructor(type: PropertyType, value: any, metadata: any) {
        this.type = type
        this.value = value
        this.metadata = metadata
    }

}

export const enum PropertyType {
    Join = "Join",
    Alias = "Alias",
    Transformations = "Transformations",
    DroppedAttributes = "Dropped Attributes",
    Aggregate = "Aggregate",
    Grouping = "Grouping",
    InputSource = "InputSource",
    OutputSource = "OutpuSource",
    SourceType = "Source Type",
    Sort = "Sort",
    Filter = "Filter",
    Properties = "Properties"
}
