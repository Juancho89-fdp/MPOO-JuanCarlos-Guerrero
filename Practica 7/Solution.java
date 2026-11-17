import java.io.*;
import java.util.*;

/* =====================================================
   ENUMS
   ===================================================== */

enum TipoDestino {
    PLANETA,
    SATELITE_NATURAL
}

enum TipoRecurso {
    DRON,
    SATELITE_ARTIFICIAL,
    CARGA,
    TRIPULANTE
}

enum TipoMision {
    EXPLORACION,
    RESCATE,
    TRANSPORTE
}

enum Capacidad {
    COMUNICABLE,
    RASTREABLE,
    OPERABLE,
    ESCANEABLE
}

enum EstadoValidacion {
    OK,
    FALLIDA
}

enum EstadoPaso {
    OK,
    NO_EJECUTADO
}

enum ResultadoObjetivo {
    ESCANEO_COMPLETADO,
    RESCATE_COMPLETADO,
    CARGA_ENTREGADA,
    NO_EJECUTADO
}

/* =====================================================
   DTO DESTINO
   ===================================================== */

final class DestinoDTO {

    private final TipoDestino tipo;
    private final String codigo;
    private final String nombre;
    private final double gravedad;
    private final String datoEspecifico;

    public DestinoDTO(
            TipoDestino tipo,
            String codigo,
            String nombre,
            double gravedad,
            String datoEspecifico) {

        this.tipo = tipo;
        this.codigo = codigo;
        this.nombre = nombre;
        this.gravedad = gravedad;
        this.datoEspecifico = datoEspecifico;
    }

    public TipoDestino getTipo() { return tipo; }
    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public double getGravedad() { return gravedad; }
    public String getDatoEspecifico() { return datoEspecifico; }
}

/* =====================================================
   DTO NAVE
   ===================================================== */

final class NaveDTO {

    private final String id;
    private final String nombre;
    private final String posicion;
    private final boolean operativa;

    public NaveDTO(
            String id,
            String nombre,
            String posicion,
            boolean operativa) {

        this.id = id;
        this.nombre = nombre;
        this.posicion = posicion;
        this.operativa = operativa;
    }

    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getPosicion() { return posicion; }
    public boolean isOperativa() { return operativa; }
}

/* =====================================================
   DTO BASE DE RECURSOS
   ===================================================== */

abstract class RecursoDTO {

    private final TipoRecurso tipo;
    private final String id;

    protected RecursoDTO(TipoRecurso tipo, String id) {
        this.tipo = tipo;
        this.id = id;
    }

    public TipoRecurso getTipo() { return tipo; }
    public String getId() { return id; }
}

/* =====================================================
   DTO DRON
   ===================================================== */

final class DronDTO extends RecursoDTO {

    private final String modelo;
    private final String posicion;
    private final boolean operativo;

    public DronDTO(
            String id,
            String modelo,
            String posicion,
            boolean operativo) {
        super(TipoRecurso.DRON, id);
        this.modelo = modelo;
        this.posicion = posicion;
        this.operativo = operativo;
    }

    public String getModelo() { return modelo; }
    public String getPosicion() { return posicion; }
    public boolean isOperativo() { return operativo; }
}

/* =====================================================
   DTO SATELITE ARTIFICIAL
   ===================================================== */

final class SateliteArtificialDTO extends RecursoDTO {

    private final String nombre;
    private final String posicion;
    private final boolean operativo;

    public SateliteArtificialDTO(
            String id,
            String nombre,
            String posicion,
            boolean operativo) {
        super(TipoRecurso.SATELITE_ARTIFICIAL, id);
        this.nombre = nombre;
        this.posicion = posicion;
        this.operativo = operativo;
    }

    public String getNombre() { return nombre; }
    public String getPosicion() { return posicion; }
    public boolean isOperativo() { return operativo; }
}

/* =====================================================
   DTO CARGA
   ===================================================== */

final class CargaDTO extends RecursoDTO {

    private final String descripcion;
    private final double peso;
    private final String posicion;

    public CargaDTO(
            String id,
            String descripcion,
            double peso,
            String posicion) {
        super(TipoRecurso.CARGA, id);
        this.descripcion = descripcion;
        this.peso = peso;
        this.posicion = posicion;
    }

    public String getDescripcion() { return descripcion; }
    public double getPeso() { return peso; }
    public String getPosicion() { return posicion; }
}

/* =====================================================
   DTO TRIPULANTE
   ===================================================== */

final class TripulanteDTO extends RecursoDTO {

    private final String nombre;
    private final String posicion;

    public TripulanteDTO(
            String id,
            String nombre,
            String posicion) {
        super(TipoRecurso.TRIPULANTE, id);
        this.nombre = nombre;
        this.posicion = posicion;
    }

    public String getNombre() { return nombre; }
    public String getPosicion() { return posicion; }
}

/* =====================================================
   DTO MISION
   ===================================================== */

final class MisionDTO {

    private final String id;
    private final String nombre;
    private final TipoMision tipo;
    private final List idsRecursos;

    public MisionDTO(
            String id,
            String nombre,
            TipoMision tipo,
            List idsRecursos) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.idsRecursos = new ArrayList(idsRecursos);
    }

    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public TipoMision getTipo() { return tipo; }
    public List getIdsRecursos() {
        return new ArrayList(idsRecursos);
    }
}

/* =====================================================
   RESULTADO DE UNA MISION
   ===================================================== */

final class EjecucionMisionDTO {

    private final EstadoValidacion validacion;
    private final EstadoPaso preparacion;
    private final EstadoPaso viaje;
    private final ResultadoObjetivo objetivo;
    private final EstadoPaso finalizacion;
    private final EstadoPaso reporte;

    public EjecucionMisionDTO(
            EstadoValidacion validacion,
            EstadoPaso preparacion,
            EstadoPaso viaje,
            ResultadoObjetivo objetivo,
            EstadoPaso finalizacion,
            EstadoPaso reporte) {
        this.validacion = validacion;
        this.preparacion = preparacion;
        this.viaje = viaje;
        this.objetivo = objetivo;
        this.finalizacion = finalizacion;
        this.reporte = reporte;
    }

    public EstadoValidacion getValidacion() { return validacion; }
    public EstadoPaso getPreparacion() { return preparacion; }
    public EstadoPaso getViaje() { return viaje; }
    public ResultadoObjetivo getObjetivo() { return objetivo; }
    public EstadoPaso getFinalizacion() { return finalizacion; }
    public EstadoPaso getReporte() { return reporte; }
}

/* =====================================================
   CONTRATOS DE MAPPERS
   ===================================================== */

interface DestinoMapper {
    DestinoEspacial toEntity(DestinoDTO dto);
}

interface NaveMapper {
    Nave toEntity(NaveDTO dto);
}

interface RecursoMapper {
    Recurso toEntity(RecursoDTO dto);
}

/* =====================================================
   SERVICIO DE CREACION DE MISIONES
   ===================================================== */

interface MisionFactoryService {
    Mision crearMision(
            MisionDTO dto,
            Nave nave,
            DestinoEspacial destino,
            Map recursos);
}

/* =====================================================
   SERVICIO DE CAPACIDADES
   ===================================================== */

interface CapacidadService {
    List obtenerCapacidades(Object entidad);
}


/* =====================================================
   IMPLEMENTACION DEL ALUMNO
   ===================================================== */

/* 1. INTERFACES DE CAPACIDADES */
interface Comunicable { String comunicar(); }
interface Rastreable { String obtenerPosicion(); }
interface Operable { boolean estaOperativo(); }
interface Escaneable { String escanear(); }

/* 2. DESTINOS ESPACIALES */
abstract class DestinoEspacial {
    private final String codigo;
    private final String nombre;
    private final double gravedad;

    protected DestinoEspacial(String codigo, String nombre, double gravedad) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.gravedad = gravedad;
    }

    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public double getGravedad() { return gravedad; }
}

class Planeta extends DestinoEspacial {
    private final boolean tieneAtmosfera;

    public Planeta(String codigo, String nombre, double gravedad, boolean tieneAtmosfera) {
        super(codigo, nombre, gravedad);
        this.tieneAtmosfera = tieneAtmosfera;
    }

    public boolean tieneAtmosfera() { return tieneAtmosfera; }
}

class SateliteNatural extends DestinoEspacial {
    private final String orbitaA;

    public SateliteNatural(String codigo, String nombre, double gravedad, String orbitaA) {
        super(codigo, nombre, gravedad);
        this.orbitaA = orbitaA;
    }

    public String getOrbitaA() { return orbitaA; }
}

/* 3. NAVE */
class Nave implements Comunicable, Rastreable, Operable {
    private final String id;
    private final String nombre;
    private final String posicion;
    private final boolean operativa;

    public Nave(String id, String nombre, String posicion, boolean operativa) {
        this.id = id;
        this.nombre = nombre;
        this.posicion = posicion;
        this.operativa = operativa;
    }

    public String getId() { return id; }
    public String getNombre() { return nombre; }

    @Override public String comunicar() { return "Nave comunicando"; }
    @Override public String obtenerPosicion() { return posicion; }
    @Override public boolean estaOperativo() { return operativa; }
}

/* 4 y 5. RECURSOS Y SUS CAPACIDADES */
abstract class Recurso {
    private final String id;
    protected Recurso(String id) { this.id = id; }
    public String getId() { return id; }
}

class Dron extends Recurso implements Comunicable, Rastreable, Operable, Escaneable {
    private final String modelo;
    private final String posicion;
    private final boolean operativo;

    public Dron(String id, String modelo, String posicion, boolean operativo) {
        super(id);
        this.modelo = modelo;
        this.posicion = posicion;
        this.operativo = operativo;
    }

    public String getModelo() { return modelo; }
    @Override public String comunicar() { return "Dron comunicando"; }
    @Override public String obtenerPosicion() { return posicion; }
    @Override public boolean estaOperativo() { return operativo; }
    @Override public String escanear() { return "Escaneando Dron"; }
}

class SateliteArtificial extends Recurso implements Comunicable, Rastreable, Operable, Escaneable {
    private final String nombre;
    private final String posicion;
    private final boolean operativo;

    public SateliteArtificial(String id, String nombre, String posicion, boolean operativo) {
        super(id);
        this.nombre = nombre;
        this.posicion = posicion;
        this.operativo = operativo;
    }

    public String getNombre() { return nombre; }
    @Override public String comunicar() { return "Satelite comunicando"; }
    @Override public String obtenerPosicion() { return posicion; }
    @Override public boolean estaOperativo() { return operativo; }
    @Override public String escanear() { return "Escaneando Satelite"; }
}

class Carga extends Recurso implements Rastreable {
    private final String descripcion;
    private final double peso;
    private final String posicion;

    public Carga(String id, String descripcion, double peso, String posicion) {
        super(id);
        this.descripcion = descripcion;
        this.peso = peso;
        this.posicion = posicion;
    }

    public String getDescripcion() { return descripcion; }
    public double getPeso() { return peso; }
    @Override public String obtenerPosicion() { return posicion; }
}

class Tripulante extends Recurso implements Comunicable, Rastreable {
    private final String nombre;
    private final String posicion;

    public Tripulante(String id, String nombre, String posicion) {
        super(id);
        this.nombre = nombre;
        this.posicion = posicion;
    }

    public String getNombre() { return nombre; }
    @Override public String comunicar() { return "Tripulante comunicando"; }
    @Override public String obtenerPosicion() { return posicion; }
}

/* 6, 7, 8 y 9. JERARQUIA DE MISIONES Y TEMPLATE METHOD */
abstract class Mision {
    private final String id;
    private final String nombre;
    protected final Nave nave;
    protected final DestinoEspacial destino;

    protected Mision(String id, String nombre, Nave nave, DestinoEspacial destino) {
        this.id = id;
        this.nombre = nombre;
        this.nave = nave;
        this.destino = destino;
    }

    public String getId() { return id; }
    public String getNombre() { return nombre; }

    public final EjecucionMisionDTO ejecutarMision() {
        if (!nave.estaOperativo()) {
            return new EjecucionMisionDTO(
                    EstadoValidacion.FALLIDA,
                    EstadoPaso.NO_EJECUTADO,
                    EstadoPaso.NO_EJECUTADO,
                    ResultadoObjetivo.NO_EJECUTADO,
                    EstadoPaso.NO_EJECUTADO,
                    EstadoPaso.NO_EJECUTADO
            );
        }

        return new EjecucionMisionDTO(
                EstadoValidacion.OK,
                EstadoPaso.OK,
                EstadoPaso.OK,
                ejecutarObjetivo(),
                EstadoPaso.OK,
                EstadoPaso.OK
        );
    }

    protected abstract ResultadoObjetivo ejecutarObjetivo();
}

class MisionExploracion extends Mision {
    public MisionExploracion(String id, String nombre, Nave nave, DestinoEspacial destino) {
        super(id, nombre, nave, destino);
    }
    @Override protected ResultadoObjetivo ejecutarObjetivo() {
        return ResultadoObjetivo.ESCANEO_COMPLETADO;
    }
}

class MisionRescate extends Mision {
    public MisionRescate(String id, String nombre, Nave nave, DestinoEspacial destino) {
        super(id, nombre, nave, destino);
    }
    @Override protected ResultadoObjetivo ejecutarObjetivo() {
        return ResultadoObjetivo.RESCATE_COMPLETADO;
    }
}

class MisionTransporte extends Mision {
    public MisionTransporte(String id, String nombre, Nave nave, DestinoEspacial destino) {
        super(id, nombre, nave, destino);
    }
    @Override protected ResultadoObjetivo ejecutarObjetivo() {
        return ResultadoObjetivo.CARGA_ENTREGADA;
    }
}

/* 11. MAPPERS */
class DestinoMapperImpl implements DestinoMapper {
    @Override public DestinoEspacial toEntity(DestinoDTO dto) {
        if (dto.getTipo() == TipoDestino.PLANETA) {
            return new Planeta(dto.getCodigo(), dto.getNombre(), dto.getGravedad(), Boolean.parseBoolean(dto.getDatoEspecifico()));
        } else if (dto.getTipo() == TipoDestino.SATELITE_NATURAL) {
            return new SateliteNatural(dto.getCodigo(), dto.getNombre(), dto.getGravedad(), dto.getDatoEspecifico());
        }
        return null;
    }
}

class NaveMapperImpl implements NaveMapper {
    @Override public Nave toEntity(NaveDTO dto) {
        return new Nave(dto.getId(), dto.getNombre(), dto.getPosicion(), dto.isOperativa());
    }
}

class RecursoMapperImpl implements RecursoMapper {
    @Override public Recurso toEntity(RecursoDTO dto) {
        if (dto instanceof DronDTO) {
            DronDTO d = (DronDTO) dto;
            return new Dron(d.getId(), d.getModelo(), d.getPosicion(), d.isOperativo());
        } else if (dto instanceof SateliteArtificialDTO) {
            SateliteArtificialDTO s = (SateliteArtificialDTO) dto;
            return new SateliteArtificial(s.getId(), s.getNombre(), s.getPosicion(), s.isOperativo());
        } else if (dto instanceof CargaDTO) {
            CargaDTO c = (CargaDTO) dto;
            return new Carga(c.getId(), c.getDescripcion(), c.getPeso(), c.getPosicion());
        } else if (dto instanceof TripulanteDTO) {
            TripulanteDTO t = (TripulanteDTO) dto;
            return new Tripulante(t.getId(), t.getNombre(), t.getPosicion());
        }
        return null;
    }
}

/* 12. FACTORY DE MISIONES */
class MisionFactoryServiceImpl implements MisionFactoryService {
    @Override
    public Mision crearMision(MisionDTO dto, Nave nave, DestinoEspacial destino, Map recursos) {
        switch (dto.getTipo()) {
            case EXPLORACION:
                return new MisionExploracion(dto.getId(), dto.getNombre(), nave, destino);
            case RESCATE:
                return new MisionRescate(dto.getId(), dto.getNombre(), nave, destino);
            case TRANSPORTE:
                return new MisionTransporte(dto.getId(), dto.getNombre(), nave, destino);
            default:
                return null;
        }
    }
}

/* 13. SERVICIO DE CAPACIDADES */
class CapacidadServiceImpl implements CapacidadService {
    @Override
    public List obtenerCapacidades(Object entidad) {
        List capacidades = new ArrayList();

        if (entidad instanceof Comunicable) {
            capacidades.add(Capacidad.COMUNICABLE);
        }
        if (entidad instanceof Rastreable) {
            capacidades.add(Capacidad.RASTREABLE);
        }
        if (entidad instanceof Operable) {
            capacidades.add(Capacidad.OPERABLE);
        }
        if (entidad instanceof Escaneable) {
            capacidades.add(Capacidad.ESCANEABLE);
        }

        return capacidades;
    }
}

/* =====================================================
   MAIN Y METODOS PROPORCIONADOS (NO TOCAR)
   ===================================================== */

public class Solution {

    public static void main(String[] args)
            throws Exception {

        BufferedReader br =
                new BufferedReader(
                        new InputStreamReader(System.in));

        String destinoInput = br.readLine();
        String naveInput = br.readLine();
        String recursosInput = br.readLine();
        String misionInput = br.readLine();

        String resultado =
                procesarMision(
                        destinoInput,
                        naveInput,
                        recursosInput,
                        misionInput
                );

        System.out.print(resultado);
    }

    /* =====================================================
       PROCESAMIENTO PRINCIPAL
       ===================================================== */

    public static String procesarMision(
            String destinoInput,
            String naveInput,
            String recursosInput,
            String misionInput) {

        DestinoDTO destinoDTO =
                parseDestino(destinoInput);

        NaveDTO naveDTO =
                parseNave(naveInput);

        List recursosDTO =
                parseRecursos(recursosInput);

        MisionDTO misionDTO =
                parseMision(misionInput);

        /* MAPPERS DEL ALUMNO */
        DestinoMapper destinoMapper =
                new DestinoMapperImpl();

        NaveMapper naveMapper =
                new NaveMapperImpl();

        RecursoMapper recursoMapper =
                new RecursoMapperImpl();

        /* SERVICIOS DEL ALUMNO */
        MisionFactoryService factory =
                new MisionFactoryServiceImpl();

        CapacidadService capacidadService =
                new CapacidadServiceImpl();

        /* DTO -> ENTIDADES */
        DestinoEspacial destino =
                destinoMapper.toEntity(destinoDTO);

        Nave nave =
                naveMapper.toEntity(naveDTO);

        Map recursos =
                mapearRecursos(
                        recursosDTO,
                        recursoMapper
                );

        /* CREAR MISION */
        Mision mision =
                factory.crearMision(
                        misionDTO,
                        nave,
                        destino,
                        recursos
                );

        /* EJECUTAR MISION */
        EjecucionMisionDTO ejecucion =
                mision.ejecutarMision();

        /* HACKERRANK CONSTRUYE LA SALIDA */
        return construirSalida(
                destino,
                nave,
                recursos,
                misionDTO,
                ejecucion,
                capacidadService
        );
    }

    /* =====================================================
       MAPEAR RECURSOS
       ===================================================== */

    private static Map mapearRecursos(
            List recursosDTO,
            RecursoMapper mapper) {

        Map recursos =
                new LinkedHashMap();

        for (Object objDto : recursosDTO) {
            RecursoDTO dto = (RecursoDTO) objDto;
            Recurso recurso =
                    mapper.toEntity(dto);

            recursos.put(
                    recurso.getId(),
                    recurso
            );
        }

        return recursos;
    }

    /* =====================================================
       SALIDA
       ===================================================== */

    private static String construirSalida(
            DestinoEspacial destino,
            Nave nave,
            Map recursos,
            MisionDTO misionDTO,
            EjecucionMisionDTO ejecucion,
            CapacidadService capacidadService) {

        List salida =
                new ArrayList();

        salida.add(
                "MISION "
                        + misionDTO.getId());

        salida.add(
                "TIPO "
                        + misionDTO.getTipo());

        salida.add(
                construirDestino(destino));

        salida.add(
                construirCapacidades(
                        nave.getId(),
                        nave,
                        capacidadService
                )
        );

        for (Object objId
                : misionDTO.getIdsRecursos()) {

            String id = (String) objId;
            Recurso recurso =
                    (Recurso) recursos.get(id);

            if (recurso != null) {
                salida.add(
                        construirCapacidades(
                                recurso.getId(),
                                recurso,
                                capacidadService
                        )
                );
            }
        }

        /* VALIDACION */
        if (ejecucion.getValidacion()
                == EstadoValidacion.FALLIDA) {

            salida.add(
                    "VALIDACION FALLIDA");

            salida.add(
                    "MISION ABORTADA");

            return String.join(
                    "\n",
                    salida
            );
        }

        salida.add(
                "VALIDACION OK");

        /* PREPARACION */
        if (ejecucion.getPreparacion()
                == EstadoPaso.OK) {
            salida.add(
                    "PREPARACION OK");
        }

        /* VIAJE */
        if (ejecucion.getViaje()
                == EstadoPaso.OK) {
            salida.add(
                    "VIAJE "
                            + destino.getNombre());
        }

        /* OBJETIVO */
        if (ejecucion.getObjetivo()
                != ResultadoObjetivo.NO_EJECUTADO) {
            salida.add(
                    "OBJETIVO "
                            + ejecucion
                                    .getObjetivo()
                                    .name());
        }

        /* FINALIZACION */
        if (ejecucion.getFinalizacion()
                == EstadoPaso.OK) {
            salida.add(
                    "FINALIZACION OK");
        }

        /* REPORTE */
        if (ejecucion.getReporte()
                == EstadoPaso.OK) {
            salida.add(
                    "REPORTE COMPLETADO");
        }

        return String.join(
                "\n",
                salida
        );
    }

    /* =====================================================
       DESTINO
       ===================================================== */

    private static String construirDestino(
            DestinoEspacial destino) {

        if (destino instanceof Planeta) {

            Planeta planeta =
                    (Planeta) destino;

            return "DESTINO PLANETA "
                    + planeta.getNombre()
                    + " ATMOSFERA "
                    + (
                        planeta.tieneAtmosfera()
                                ? "SI"
                                : "NO"
                    );
        }

        if (destino instanceof SateliteNatural) {

            SateliteNatural satelite =
                    (SateliteNatural) destino;

            return "DESTINO SATELITE_NATURAL "
                    + satelite.getNombre()
                    + " ORBITA "
                    + satelite.getOrbitaA();
        }

        throw new IllegalArgumentException(
                "Destino no valido");
    }

    /* =====================================================
       CAPACIDADES
       ===================================================== */

    private static String construirCapacidades(
            String id,
            Object entidad,
            CapacidadService servicio) {

        List capacidades =
                servicio.obtenerCapacidades(
                        entidad);

        List nombres =
                new ArrayList();

        for (Object objCap
                : capacidades) {
            Capacidad capacidad = (Capacidad) objCap;
            nombres.add(
                    capacidad.name());
        }

        return "CAPACIDADES "
                + id
                + " "
                + String.join(
                        ",",
                        nombres
                );
    }

    /* =====================================================
       PARSE DESTINO
       ===================================================== */

    private static DestinoDTO parseDestino(
            String input) {

        String[] d =
                input.split("\\|", 5);

        return new DestinoDTO(
                TipoDestino.valueOf(
                        d[0].trim()),
                d[1].trim(),
                d[2].trim(),
                Double.parseDouble(
                        d[3].trim()),
                d[4].trim()
        );
    }

    /* =====================================================
       PARSE NAVE
       ===================================================== */

    private static NaveDTO parseNave(
            String input) {

        String[] d =
                input.split("\\|", 4);

        return new NaveDTO(
                d[0].trim(),
                d[1].trim(),
                d[2].trim(),
                Boolean.parseBoolean(
                        d[3].trim())
        );
    }

    /* =====================================================
       PARSE RECURSOS
       ===================================================== */

    private static List parseRecursos(
            String input) {

        List recursos =
                new ArrayList();

        if (input == null
                || input.trim().isEmpty()
                || input.equals("-")) {
            return recursos;
        }

        String[] registros =
                input.split(";");

        for (String registro : registros) {

            String[] d =
                    registro.split("\\|");

            TipoRecurso tipo =
                    TipoRecurso.valueOf(
                            d[0].trim());

            switch (tipo) {

                case DRON:
                    recursos.add(
                            new DronDTO(
                                    d[1].trim(),
                                    d[2].trim(),
                                    d[3].trim(),
                                    Boolean.parseBoolean(
                                            d[4].trim())
                            )
                    );
                    break;

                case SATELITE_ARTIFICIAL:
                    recursos.add(
                            new SateliteArtificialDTO(
                                    d[1].trim(),
                                    d[2].trim(),
                                    d[3].trim(),
                                    Boolean.parseBoolean(
                                            d[4].trim())
                            )
                    );
                    break;

                case CARGA:
                    recursos.add(
                            new CargaDTO(
                                    d[1].trim(),
                                    d[2].trim(),
                                    Double.parseDouble(
                                            d[3].trim()),
                                    d[4].trim()
                            )
                    );
                    break;

                case TRIPULANTE:
                    recursos.add(
                            new TripulanteDTO(
                                    d[1].trim(),
                                    d[2].trim(),
                                    d[3].trim()
                            )
                    );
                    break;
            }
        }

        return recursos;
    }

    /* =====================================================
       PARSE MISION
       ===================================================== */

    private static MisionDTO parseMision(
            String input) {

        String[] d =
                input.split("\\|", 4);

        List ids =
                new ArrayList();

        if (d.length == 4
                && !d[3].trim().isEmpty()
                && !d[3].equals("-")) {

            String[] recursos =
                    d[3].split(",");

            for (String id : recursos) {
                ids.add(
                        id.trim());
            }
        }

        return new MisionDTO(
                d[0].trim(),
                d[1].trim(),
                TipoMision.valueOf(
                        d[2].trim()),
                ids
        );
    }
}
