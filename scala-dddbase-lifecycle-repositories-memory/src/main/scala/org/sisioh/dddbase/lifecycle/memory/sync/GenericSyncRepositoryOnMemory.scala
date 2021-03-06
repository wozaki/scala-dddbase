/*
 * Copyright 2010 TRICREO, Inc. (http://tricreo.jp/)
 * Copyright 2011 Sisioh Project and others. (http://www.sisioh.org/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.sisioh.dddbase.lifecycle.memory.sync

import org.sisioh.dddbase.core.lifecycle.sync.SyncEntityIOContext
import org.sisioh.dddbase.core.model.{Identifier, EntityCloneable, Entity}

/**
 * 汎用的な非同期型オンメモリ不変リポジトリ。
 *
 * @tparam ID 識別子の型
 * @tparam E エンティティの型
 */
class GenericSyncRepositoryOnMemory[ID <: Identifier[_], E <: Entity[ID] with EntityCloneable[ID, E] with Ordered[E]]
(entities: Map[ID, E] = Map.empty[ID, E])
  extends AbstractSyncRepositoryOnMemory[ID, E](entities) {

  type This = GenericSyncRepositoryOnMemory[ID, E]

  override protected def createInstance(entities: Map[ID, E]): GenericSyncRepositoryOnMemory[ID, E]#This =
    new GenericSyncRepositoryOnMemory(entities)
}

/**
 * コンパニオンオブジェクト。
 */
object GenericSyncRepositoryOnMemory {

  object Implicits {

    implicit val defaultEntityIOContext = SyncEntityIOContext

  }

  /**
   * ファクトリメソッド。
   *
   * @tparam ID 識別子の型
   * @tparam E エンティティの型
   * @return [[org.sisioh.dddbase.lifecycle.memory.sync.GenericSyncRepositoryOnMemory]]
   */
  def apply[ID <: Identifier[_], E <: Entity[ID] with EntityCloneable[ID, E] with Ordered[E]]
  (entities: Map[ID, E] = Map.empty[ID, E]) =
    new GenericSyncRepositoryOnMemory[ID, E](entities)

  def unapply[ID <: Identifier[_], E <: Entity[ID] with EntityCloneable[ID, E] with Ordered[E]]
  (repository: GenericSyncRepositoryOnMemory[ID, E]): Option[(Map[ID, E])] =
    Some(repository.entities)

}