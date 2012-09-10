//The contents of this file are subject to the Mozilla Public License Version 1.1
//(the "License"); you may not use this file except in compliance with the
//License. You may obtain a copy of the License at http://www.mozilla.org/MPL/
//
//Software distributed under the License is distributed on an "AS IS" basis,
//WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
//for the specific language governing rights and
//limitations under the License.
//
//The Original Code is "The Columba Project"
//
//The Initial Developers of the Original Code are Frederik Dietz and Timo Stich.
//Portions created by Frederik Dietz and Timo Stich are Copyright (C) 2003.
//
//All Rights Reserved.
package org.columba.core.command;

import org.columba.api.command.IWorkerStatusController;
import org.junit.Ignore;

@Ignore
public class TestCommand extends Command {
  private int id;

  private DefaultProcessorTest test;

  public TestCommand(DefaultProcessorTest test, int id) {
    this(test, id, Command.NORMAL_PRIORITY);
  }
  
  public TestCommand() {
    super(new DefaultCommandReference());
  }

  public TestCommand(DefaultProcessorTest test, int id, int priority) {
    super(new DefaultCommandReference());
    this.id = id;
    this.test = test;
    this.priority = priority;
  }

  public void updateGUI() {
    test.finishedID = id;
  }

  public void execute(IWorkerStatusController worker) throws Exception {
    test.executedID = id;
    Thread.sleep(50);
  }

  /**
   * @return Returns the id.
   */
  public int getId() {
    return id;
  }

  /**
   * @param id
   *            The id to set.
   */
  public void setId(int id) {
    this.id = id;
  }
}