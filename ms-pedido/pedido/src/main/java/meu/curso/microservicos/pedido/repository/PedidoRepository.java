package meu.curso.microservicos.pedido.repository;

import meu.curso.microservicos.pedido.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
