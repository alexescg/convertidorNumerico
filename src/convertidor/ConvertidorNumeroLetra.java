package convertidor;

/**
 * Clase que sirve como utilidad para convertir Numeros a Palabras.
 *
 * @author Alejandro Escobedo
 * @since Nov 20, 2015
 * @version 1.0
 */
public class ConvertidorNumeroLetra {

    public Double num;

    private final double milMillones;

    private final double millones;

    private final double millares;

    private final double centenas;

    private final double decenas;

    private double unidades;

    private final double decimos;

    private double centesimos;

    private StringBuilder writtenNumber;

    /**
     * Constructor para el convertidor.
     *
     * @param num a convertir
     */
    public ConvertidorNumeroLetra(Double num) {
        this.num = num;
        this.decimos = Math.round((num % 1) * 100) / 10;
        this.centesimos = Math.round((num % 1) * 100) % 10;
        this.num -= (num % 1);
        this.milMillones = (num / 1000000000d) % 1000;
        this.millones = (num / 1000000) % 1000;
        this.millares = (num / 1000) % 1000;
        this.centenas = (num / 100) % 10;
        this.decenas = (num / 10) % 10;
        this.unidades = (num % 10);
    }

    public enum Unidades {

        CERO, UNO, DOS, TRES, CUATRO, CINCO, SEIS, SIETE, OCHO, NUEVE
    }

    public enum Decenas {

        X, Y, VEINTE, TREINTA, CUARENTA, CINCUENTA, SESENTA, SETENTA, OCHENTA, NOVENTA
    }

    public enum Dieces {

        DIEZ, ONCE, DOCE, TRECE, CATORCE, QUINCE, DIECISEIS, DIECISIETE, DIECIOCHO, DIECINUEVE
    }

    public enum Veintes {

        X(""), UN("UN"), DOS("DOS"), TRES("TRES"), CUATRO("CUATRO"), CINCO("CINCO"), SEIS("SEIS"), SIETE("SIETE"), OCHO("OCHO"), NUEVE("NUEVE");

        public String descripcion;

        private Veintes(String descripcion) {
            this.descripcion = descripcion;
        }

        /**
         * Devuelve la descripción.
         *
         * @return
         */
        public String getDescripcion() {
            return descripcion;
        }

        /**
         * Devuelve la descripción de la enumeracion.
         *
         * @return descripción
         */
        @Override
        public String toString() {
            return this.getDescripcion();
        }

    }

    public enum UnidadesDecenas {

        X(""), UN("UN"), DOS("DOS"), TRES("TRES"), CUATRO("CUATRO"), CINCO("CINCO"), SEIS("SEIS"), SIETE("SIETE"), OCHO("OCHO"), NUEVE("NUEVE");

        public String descripcion;

        private UnidadesDecenas(String descripcion) {
            this.descripcion = descripcion;
        }

        /**
         * Devuelve la descripción.
         *
         * @return
         */
        public String getDescripcion() {
            return descripcion;
        }

        /**
         * Devuelve la descripción de la enumeracion.
         *
         * @return descripción
         */
        @Override
        public String toString() {
            return this.getDescripcion();
        }

    }

    public enum Cientos {

        X, CIEN, DOSCIENTOS, TRESCIENTOS, CUATROCIENTOS, QUINIENTOS, SEISCIENTOS, SETECIENTOS, OCHOCIENTOS, NOVECIENTOS
    }

    public enum ConstantesNumeros {

        UN_MILMILLONES("MIL DE MILLONES"), MILMILLON("MIL MILLONES"), MILMILLONES("MILES DE MILLONES"), MILLONES("MILLONES"), UN_MILLON("UN MILLON"), UN_MIL("UN MIL"), MIL("MIL"), CIEN("CIEN"), CIENTO("CIENTO"), VEINTI("VEINTI"), ESPACIO(" "), Y("Y");

        public String descripcion;

        private ConstantesNumeros(String descripcion) {
            this.descripcion = descripcion;
        }

        /**
         * Devuelve la descripción.
         *
         * @return
         */
        public String getDescripcion() {
            return descripcion;
        }

        /**
         * Devuelve la descripción de la enumeracion.
         *
         * @return descripción
         */
        @Override
        public String toString() {
            return this.getDescripcion();
        }

    }

    /**
     * Convertir número de formato numerico a escrito.
     *
     * @return número convertido
     */
    public String convert() {
        writtenNumber = new StringBuilder();
        this.getMilesMillones();
        this.getMillones();
        this.getMillares();

        //centenas
        if ((int) this.centenas == 1) {
            writtenNumber.append(num % 100 == 0 ? ConstantesNumeros.CIEN : ConstantesNumeros.CIENTO);
        } else if ((int) this.centenas > 0) {
            writtenNumber.append(" ").append(Cientos.values()[(int) this.centenas]);
        }
        //decenas
        if ((int) this.decenas == 1) {
            writtenNumber.append(ConstantesNumeros.ESPACIO).append(Dieces.values()[(int) this.decenas % 10]);
            this.unidades = 0;
        } else if ((int) this.decenas == 2) {
            writtenNumber.append(ConstantesNumeros.ESPACIO).append(ConstantesNumeros.VEINTI).append(Unidades.values()[(int) this.unidades]);
            this.unidades = 0;
        } else if ((int) this.decenas > 1) {
            writtenNumber.append(ConstantesNumeros.ESPACIO).append(Decenas.values()[(int) this.decenas]);
            if ((int) this.unidades > 0) {
                writtenNumber.append(ConstantesNumeros.ESPACIO).append(ConstantesNumeros.Y);
            }
        }
        //unidades
        if ((int) this.unidades > 0) {
            writtenNumber.append(ConstantesNumeros.ESPACIO).append(Unidades.values()[(int) this.unidades]);
        } else if (this.num == 0) {
            writtenNumber.append(Unidades.values()[0]);
        }
        writtenNumber.append(ConstantesNumeros.ESPACIO).append("PESOS");
        this.getDecimales();

        return writtenNumber.toString().trim();
    }

    /**
     * Obtener Miles de Millones en formato escrito.
     *
     */
    public void getMilesMillones() {

        if (((int) this.milMillones == 1) && (((int) this.milMillones % 100) / 10) == 0 && ((int) this.milMillones % 10) / 10 == 0) {
            writtenNumber.append(UnidadesDecenas.values()[(int) this.milMillones]).append(ConstantesNumeros.ESPACIO).append(ConstantesNumeros.UN_MILMILLONES).append(ConstantesNumeros.ESPACIO);
        } else if ((int) this.milMillones > 0) {

            //cienes de millar  
            int tempCienDeMilMillon = (int) this.milMillones / 100;

            if (tempCienDeMilMillon == 1) {
                writtenNumber.append(this.milMillones % 100 == 0 ? ConstantesNumeros.CIEN : ConstantesNumeros.CIENTO);
            } else if (tempCienDeMilMillon > 1) {
                writtenNumber.append(Cientos.values()[tempCienDeMilMillon]);
            }

            //decenas de millar
            int tempDiezDeMilMillon = (int) (this.milMillones % 100) / 10;
            int unidadesDeMilMillon = (int) this.milMillones % 10;
            if (tempDiezDeMilMillon >= 1) {
                if (tempDiezDeMilMillon == 1) {
                    writtenNumber.append(ConstantesNumeros.ESPACIO).append(Dieces.values()[unidadesDeMilMillon]);
                    unidadesDeMilMillon = 0;

                } else if (tempDiezDeMilMillon == 2) {
                    writtenNumber.append(ConstantesNumeros.ESPACIO).append(unidadesDeMilMillon == 0 ? Decenas.values()[tempDiezDeMilMillon] : ConstantesNumeros.VEINTI);

                } else if (tempDiezDeMilMillon > 1) {
                    writtenNumber.append(ConstantesNumeros.ESPACIO).append(Decenas.values()[tempDiezDeMilMillon]);

                    if (unidadesDeMilMillon > 0) {
                        writtenNumber.append(ConstantesNumeros.ESPACIO).append(ConstantesNumeros.Y).append(ConstantesNumeros.ESPACIO);
                    }
                }
            }
            //unidades de millar
            writtenNumber.append(UnidadesDecenas.values()[unidadesDeMilMillon]).append(ConstantesNumeros.ESPACIO).append(ConstantesNumeros.MILMILLONES).append(ConstantesNumeros.ESPACIO);
        }
    }

    /**
     * Obtener Millones en formato escrito.
     *
     */
    public void getMillones() {

        if (((int) this.millones == 1) && (((int) this.millones % 100) / 10) == 0 && ((int) this.millones % 10) / 10 == 0) {
            writtenNumber.append(UnidadesDecenas.values()[(int) this.millones]).append(ConstantesNumeros.ESPACIO).append(ConstantesNumeros.UN_MILLON).append(ConstantesNumeros.ESPACIO);
        } else if ((int) this.millones > 0) {
            //cienes de millon  
            int tempCienDeMillon = (int) this.millones / 100;

            if (tempCienDeMillon == 1) {
                writtenNumber.append(this.millones % 100 == 0 ? ConstantesNumeros.CIEN : ConstantesNumeros.CIENTO);
            } else if (tempCienDeMillon > 1) {
                writtenNumber.append(Cientos.values()[tempCienDeMillon]);
            }

            //decenas de millon
            int tempDiezDeMillon = (int) (this.millones % 100) / 10;
            int unidadesDeMillon = (int) this.millones % 10;
            if (tempDiezDeMillon >= 1) {
                if (tempDiezDeMillon == 1) {
                    writtenNumber.append(ConstantesNumeros.ESPACIO).append(Dieces.values()[unidadesDeMillon]);
                    unidadesDeMillon = 0;

                } else if (tempDiezDeMillon == 2) {
                    writtenNumber.append(ConstantesNumeros.ESPACIO).append(unidadesDeMillon == 0 ? Decenas.values()[tempDiezDeMillon] : ConstantesNumeros.VEINTI);

                } else if (tempDiezDeMillon > 1) {
                    writtenNumber.append(ConstantesNumeros.ESPACIO).append(Decenas.values()[tempDiezDeMillon]);

                    if (unidadesDeMillon > 0) {
                        writtenNumber.append(ConstantesNumeros.ESPACIO).append(ConstantesNumeros.Y).append(ConstantesNumeros.ESPACIO);
                    }
                }
            }
            //unidades de millon
            writtenNumber.append(UnidadesDecenas.values()[unidadesDeMillon]).append(ConstantesNumeros.ESPACIO).append(ConstantesNumeros.MILLONES).append(ConstantesNumeros.ESPACIO);
        }
    }

    /**
     * Obtener Millares en formato escrito.
     *
     */
    public void getMillares() {

        if (((int) this.millares == 1) && (((int) this.millares % 100) / 10) == 0 && (int) this.millares % 10 == 0) {
            writtenNumber.append(UnidadesDecenas.values()[0]).append(ConstantesNumeros.ESPACIO).append(ConstantesNumeros.MIL).append(ConstantesNumeros.ESPACIO);
        } else if ((int) this.millares > 0) {

            //cienes de millar  
            int tempCienDeMillar = (int) this.millares / 100;

            if (tempCienDeMillar == 1) {
                writtenNumber.append(this.millares % 100 == 0 ? ConstantesNumeros.CIEN : ConstantesNumeros.CIENTO);
            } else if (tempCienDeMillar > 1) {
                writtenNumber.append(Cientos.values()[tempCienDeMillar]);
            }

            //decenas de millar
            int tempDiezDeMillar = (int) (this.millares % 100) / 10;
            int unidadesDeMillar = (int) this.millares % 10;
            if (tempDiezDeMillar >= 1) {
                if (tempDiezDeMillar == 1) {
                    writtenNumber.append(ConstantesNumeros.ESPACIO).append(Dieces.values()[unidadesDeMillar]);
                    unidadesDeMillar = 0;

                } else if (tempDiezDeMillar == 2) {
                    writtenNumber.append(ConstantesNumeros.ESPACIO).append(unidadesDeMillar == 0 ? Decenas.values()[tempDiezDeMillar] : ConstantesNumeros.VEINTI);

                } else if (tempDiezDeMillar > 1) {
                    writtenNumber.append(ConstantesNumeros.ESPACIO).append(Decenas.values()[tempDiezDeMillar]);

                    if (unidadesDeMillar > 0) {
                        writtenNumber.append(ConstantesNumeros.ESPACIO).append(ConstantesNumeros.Y).append(ConstantesNumeros.ESPACIO);
                    }
                }
            }
            //unidades de millar
            writtenNumber.append(UnidadesDecenas.values()[unidadesDeMillar]).append(ConstantesNumeros.ESPACIO).append(ConstantesNumeros.MIL);
        }
    }

    /**
     * Agrega los decimales al numero
     */
    public void getDecimales() {

        writtenNumber.append(ConstantesNumeros.ESPACIO).append("CON").append(ConstantesNumeros.ESPACIO);
        if ((int) this.decimos == 0 && (int) this.centesimos == 0) {
            writtenNumber.append(Unidades.values()[0]);
        } else {
            //decimas
            if ((int) this.decimos == 1) {
                writtenNumber.append(ConstantesNumeros.ESPACIO).append(Dieces.values()[(int) this.centesimos]);
                this.centesimos = 0;

            } else if ((int) this.decimos == 2) {
                writtenNumber.append(ConstantesNumeros.ESPACIO).append((int) this.centesimos == 0 ? Decenas.values()[(int) this.decimos] : ConstantesNumeros.VEINTI);

            } else if ((int) this.decimos > 1) {
                writtenNumber.append(ConstantesNumeros.ESPACIO).append(Decenas.values()[(int) this.decimos]);

                if ((int) this.centesimos > 0) {
                    writtenNumber.append(ConstantesNumeros.ESPACIO).append(ConstantesNumeros.Y);
                }
            }
            //unidades
            writtenNumber.append(ConstantesNumeros.ESPACIO).append(UnidadesDecenas.values()[(int) this.centesimos]);
        }
        writtenNumber.append(ConstantesNumeros.ESPACIO).append("CENTAVOS");
    }

}
