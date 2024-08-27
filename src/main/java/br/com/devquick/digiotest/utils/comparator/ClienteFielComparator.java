package br.com.devquick.digiotest.utils.comparator;

import br.com.devquick.digiotest.model.dto.ClienteFielDTO;
import java.util.Comparator;

public class ClienteFielComparator implements Comparator<ClienteFielDTO> {

  @Override
  public int compare(ClienteFielDTO client1, ClienteFielDTO client2) {
    // First sort by total purchases
    int totalComparison = Integer.compare(client2.getAmount(),
        client1.getAmount());
    if (totalComparison != 0) {
      return totalComparison;
    }
    // If total purchases are equal, sort by total value
    return client2.getTotal().compareTo(client1.getTotal());
  }
}
