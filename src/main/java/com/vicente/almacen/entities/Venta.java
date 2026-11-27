package com.vicente.almacen.entities;

import com.vicente.almacen.enums.EstadoVenta;
import com.vicente.almacen.exceptions.ConflictoException;
import com.vicente.almacen.exceptions.DatoInvalidoException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_SUCURSAL", nullable = false)
    private Sucursal sucursal;

    @Builder.Default
    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DetalleVenta> detalleVentas = new ArrayList<>();

    public static Venta crear(Sucursal sucursal){
        if (sucursal == null){
            throw new DatoInvalidoException("La sucursal es requerida");
        }
        return Venta.builder()
                .sucursal(sucursal)
                .estadoVenta(EstadoVenta.REGISTRADA)
                .fecha(LocalDate.now())
                .build();
    }

    public void agregarDetalle(DetalleVenta detalleVenta) {
        if (detalleVenta == null) {
            throw new IllegalArgumentException("El detalle de la venta es requerido");
        }

        detalleVenta.asignarVenta(this); //llena ID_VENTA de detalle
        this.detalleVentas.add(detalleVenta);
    }

    public BigDecimal total() {
        return detalleVentas.stream()
                .map(DetalleVenta::subtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void cancelar() {
        if (this.estadoVenta == EstadoVenta.CANCELADA) {
            throw new ConflictoException("La venta ya esta cancelada");
        }
        this.estadoVenta = EstadoVenta.CANCELADA;
    }
}