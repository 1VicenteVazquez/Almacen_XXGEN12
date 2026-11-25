package com.vicente.almacen.entities;

import com.vicente.almacen.enums.EstadoVenta;
import com.vicente.almacen.exceptions.ConflictoException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "VENTAS")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_VENTA")
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "ESTADO", nullable = false)
    private EstadoVenta estadoVenta;

    @Column(name = "FECHA", nullable = false)
    private LocalDate fecha;

    @Builder.Default
    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DetalleVenta> detalleVentas = new ArrayList<>();

    public void agregarDetalle(DetalleVenta detalleVenta) {
        if (detalleVenta == null) {
            throw new IllegalArgumentException("El detalle de la venta es requerido");
        }
        this.detalleVentas.add(detalleVenta);
    }

    public void cancelar() {
        if (this.estadoVenta == EstadoVenta.CANCELADA) {
            throw new ConflictoException("La venta ya esta cancelada");
        }
        this.estadoVenta = EstadoVenta.CANCELADA;
    }
}