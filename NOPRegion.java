//@author Jia Qi Poon poonjiaqi@gmail.com
//@keybinding Ctrl Alt w

import ghidra.app.script.GhidraScript;
import ghidra.program.model.address.Address;
import ghidra.program.model.mem.MemoryAccessException;
import ghidra.util.exception.CancelledException;

public class NOPRegion extends GhidraScript {

  private static final byte NOP_OPCODE = (byte) 0x90;

  private void nopSection(Address startAddr, Address endAddr) {

    try {
      clearListing(startAddr, endAddr);
    } catch (CancelledException e) {
      println("Cancelled!");
      return;
    }

    try {
      for (; !startAddr.equals(endAddr.add(1)); startAddr = startAddr.add(1)) {
        println(startAddr.toString());
        setByte(startAddr, NOP_OPCODE);
      }
    } catch (MemoryAccessException e) {
      println("Some memory access exception...");
      return;
    }
  }

  @Override
  public void run() throws Exception {

    if (currentSelection != null) {
      Address currAddr = currentSelection.getMinAddress();
      Address endAddr = currentSelection.getMaxAddress();
      nopSection(currAddr, endAddr);
    } else {
      println("Nothing highlighted!");
    }
  }
}
