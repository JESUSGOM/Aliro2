<?php
    require_once("db.php");
    require_once("variables.php");
    session_start();
    require 'include/user_sesion.php';
    require ("fpdf/fpdf.php");
    define('FPDF_FONTPATH', 'font/');
    
    //echo var_dump($_POST);
    $mesno = $_POST['mesano'];
    $cen = $_POST['miopcion'];
    //echo nl2br("\n");
    //echo "He recibido el mes = " .$mesno;
    //echo nl2br("\n");
    //echo "He recibido el centro = " .$cen;
    //echo nl2br("\n");
    $esnmes = substr($mesno,5,2);
    $esnano = substr($mesno,0,4);
    $dmesano = $esnano.$esnmes.'%';
    //echo $dmesano;
    //echo nl2br("\n");
    //echo $dmesano;
    //echo "Extraigo el mes = " .$esnmes;
    //echo nl2br("\n");
    //echo "Extraigo el año = " .$esnano;
    //echo nl2br("\n");
    //Ultimo dia del mes
    $elmes = intval($esnmes);
    $elaño = intval($esnano);
    $diafactura = date("d",(mktime(0,0,0,$elmes+1,1,$elaño)-1));
    //echo "Último día del mes = " .$diafactura;
    $esndia = strval($diafactura);
    $esnmes = strval($elmes);
    $esnaño = strval($esnano);
    //echo nl2br("\n");
    //Nombre del mes
    switch($esnmes){
        case "1":
            $esmes = "enero";
            break;
        case "2":
            $esmes = "febrero";
            break;
        case "3":
            $esmes = "marzo";
            break;
        case "4":
            $esmes = "abril";
            break;
        case "5":
            $esmes = "mayo";
            break;
        case "6":
            $esmes = "junio";
            break;
        case "7":
            $esmes = "julio";
            break;
        case "8":
            $esmes = "agosto";
            break;
        case "9":
            $esmes = "septiembre";
            break;
        case "10":
            $esmes = "octubre";
            break;
        case "11":
            $esmes = "noviembre";
            break;
        case "12":
            $esmes = "diciembre";
            break;
    }
    //echo nl2br("\n");
    $fechatotalfacctura = $esndia . " de " . $esmes . " de " . $esnaño;
    //echo "Fecha para factura = ".$fechatotalfacctura;
    //echo nl2br("\n");
    //$esmes = "Enero";
    $esaño = "2022";
    $esmesaño = "Enero / 2022";
    $Mesmes = strtoupper($esmes);
    $y = "SELECT * FROM Centros WHERE CenId = '" .$cen. "'";
    $ry = mysqli_query($conn, $y);
    while($fila = mysqli_fetch_row($ry)){
        $deno = $fila[1];
        $dire = $fila[2];
        $cdps = $fila[3];
        $prov = $fila[4];
    }
    $fila = 20;
    $barra = " / ";
    $sfpdf = new FPDF('L','mm','A4');
    $textotitulo = "Informe mensual Servicio ITC por Envera Empleo, S.L.U. (CEE) para el periodo: ".$esmes."/".$esnano.". Prestado en ".$deno. "";
    $textotitulo .= ".pdf";
    $sfpdf->SetTitle($textotitulo);
    $sfpdf->SetTopMargin(0);
    //***************** Añadimos página de PORTADA ***************************
    $sfpdf->AddPage('L','A4',0);
    $sfpdf->Image('img/logoitcizq.png', 5, 5, 50, 10);
    $sfpdf->Image('img/Envera_Logo.png', 235, 3, 50, 15, 'PNG' );
    //$sfpdf->Image('img/Imagen1.jpg', 15, 20, 260, 190, 'JPG');
    //$sfpdf->Image('img/PiePagina.jpg',90,5, 60,10,'JPG');
    $sfpdf->SetXY(00, 15);
    $sfpdf->SetTextColor(255,0,0);
    //$sfpdf->SetFont('Clibri','B',-);
    //$sfpdf->Cell(60,40,'SERVICIO DE RECEPCION EXPEDIENTE 870/2024');
    //$sfpdf->SetXY(5, 190);
    $sfpdf->SetFont('Arial','B',6);
    //$sfpdf->SetTextColor(255,0,0);
    //$sfpdf->SetXY(5, 100);
    $datosEnveraTenerife = utf8_decode("ENVERA EMPLEO, S.L.U.
    C/Bahía de Pollensa nº25. 28042-Madrid  CIF.:B-86363603 Tlf:917 462 081  Fax:917 477 145 
    Inscrita en el Registro Mercantil TOMO: 29.509, LIBRO: 0, FOLIO: 111; SECCIÓN: 8, HOJA: M-531080
    CEE DE TENERIFE C/Cruz Caridad nº42. 38350-Tacoronte. Tenerife. Tlf.:922 560 037");
    
    $sfpdf->MultiCell(10,500,$datosEnveraTenerife, 1, 'J');
    //$sfpdf->Cell(100, 5, utf8_decode('ENVERA EMPLEO, S.L.U.'),0,0,'L',false);
    //$sfpdf->Write(5,'ENVERA EMPLEO, S.L.U.');
    //***************** Añadimos PRIMERA página del documento ****************
    $sfpdf->AddPage('L','A4',0);
    $sfpdf->SetMargins(0, 0 , 0);
    $sfpdf->Image('img/logoitcizq.png', 5, 5, 50, 10);
    $sfpdf->Image('img/Envera_Logo.png', 235, 3, 50, 15, 'PNG' );
    $sfpdf->SetXY(15,$fila);
    $sfpdf->SetFont('Arial','B',11);
    $sfpdf->SetFillColor(218,77,98);
    $sfpdf->SetTextColor(255,255,255);
    $sfpdf->Cell(130, 5, utf8_decode('CLIENTE.:'),1,0,'L',true);
    $sfpdf->Cell(130, 5, utf8_decode('CIF.:'),1,0,'L',true);
    $fila = $fila + 6;//Item1
    if($fila >= 203){
        $fila = 20;
    }
    $sfpdf->SetXY(15,$fila);
    $sfpdf->SetFont('Arial','B',11);
    $sfpdf->SetFillColor(218,77,98);
    $sfpdf->SetTextColor(0,0,0);
    if($cen == 1){
        $sfpdf->Cell(130,5, utf8_decode('ITC SC - 0796/2021'),0, 0,'L');
    }
    if($cen == 2){
        $sfpdf->Cell(130,5, utf8_decode('ITC SC - 0107/2021'),0, 0,'L');
    }
    $sfpdf->Cell(75,5, utf8_decode(''.$fechatotalfacctura.""),0,0,"C");
    $fila = $fila + 6;//Item2
    if($fila >= 203){
        $fila = 20;
    }
    $sfpdf->SetXY(15,$fila);
    $sfpdf->SetFont('Arial','B',11);
    $sfpdf->SetFillColor(218,77,98);
    $sfpdf->SetTextColor(255,255,255);
    $sfpdf->Cell(130, 5, utf8_decode('Dirección:'),1,0,'L',true);
    $sfpdf->Cell(130, 5, utf8_decode('Persona de contacto:'),1,0,'L',true);
    $fila = $fila + 6;//Item3
    if($fila >= 203){
        $fila = 20;
    }
    $sfpdf->SetXY(15,$fila);//Item 3
    $sfpdf->SetFont('Arial','B',11);
    $sfpdf->SetFillColor(218,77,98);
    $sfpdf->SetTextColor(0,0,0);
    $sfpdf->Cell(75, 5, utf8_decode(''.$dire.''),0,0,'L');
    if($cen == "1"){
        $sfpdf->Cell(75, 5, utf8_decode('María Carmen Betancor Reula'),0,0,'L');
    }
    if($cen == "2"){
        $sfpdf->Cell(75, 5, utf8_decode('Francisco Valido Ortega'),0,0,'L');
    }
    $fila = $fila + 5;//Item4
    if($fila >= 203){
        $fila = 20;
    }
    $sfpdf->SetXY(15,$fila);//Numero fila 58
    $sfpdf->Cell(75, 5, utf8_decode(''.$cdps.'-'.$prov),0,0,'L');
    $provg = strtoupper($prov);
    $fila = $fila + 7;//Item5
    if($fila >= 203){
        $fila = 20;
    }
    $sfpdf->SetXY(15,$fila);//Numero fila 65
    $sfpdf->SetFont('Arial','B',11);
    $sfpdf->SetFillColor(218,77,98);
    $sfpdf->SetTextColor(255,255,255);
    $sfpdf->Cell(260, 5, utf8_decode('FECHA.:'),0,0,'L',true);
    $fila = $fila + 6;//Item6
    if($fila >= 203){
        $fila = 20;
    }
    $sfpdf->SetXY(15,$fila);//Numero fila 71
    $sfpdf->SetFont('Arial','B',11);
    $sfpdf->SetFillColor(255,255,255);
    $sfpdf->SetTextColor(0,0,0);
    $sfpdf->Cell(75, 5, utf8_decode('A35313170'),0,0,'L');
    $fila = $fila + 6;//Item7
    if($fila >= 203){
        $fila = 20;
    }
    $sfpdf->SetXY(15,$fila);//Numero fila 77
    $sfpdf->SetFont('Arial','B',11);
    $sfpdf->SetFillColor(218,77,98);
    //$sfpdf->SetFillColor(105,2,67);
    $sfpdf->SetTextColor(255,255,255);
    $sfpdf->Cell(260, 5, utf8_decode('LICITACIÓN:'),0,0,'L');
    $fila = $fila + 6;//Item8
    if($fila >= 203){
        $fila = 20;
    }
    $sfpdf->SetXY(15,$fila);//Numero fila 83
    $sfpdf->SetFont('Arial','B',11);
    $sfpdf->SetFillColor(255,255,255);
    $sfpdf->SetTextColor(0,0,0);
    $sfpdf->Cell(260,5,utf8_decode('Servicio de auxiliares de recepción para varios centros del Instituto Tecnológico de Canarias, S.A. (ITC)'),0,0,'L');
    $fila = $fila + 5;//Item9
    if($fila >= 203){
        $fila = 20;
    }
    //$sfpdf->SetXY(15, $fila);//Numero fila 88
    //$sfpdf->Cell(150,5,utf8_decode('de Canarias, S.A. (ITC).'),0,0,'L');
    $sfpdf->SetDrawColor(155, 155, 155);
    $sfpdf->SetLineWidth(0.5);
    $sfpdf->Line(15,95,260,95);
    $fila = $fila + 9;//Item10
    if($fila >= 203){
        $fila = 20;
    }
    $sfpdf->SetXY(15,$fila);//Numero fila 97
    $sfpdf->SetFont('Arial','B',11);
    $sfpdf->SetFillColor(255,255,255);
    $sfpdf->SetTextColor(0,0,0);
    /**echo $esnmes;
    echo nl2br("\n");
    echo $esmes;
    */
    $sfpdf->Cell(150,5,utf8_decode('INFORME MENSUAL CORRESPONDIENTE AL MES DE '.$Mesmes.''),0,0,'L');
    $fila = $fila + 7;//Item11
    if($fila >= 203){
        $fila = 20;
    }
    $sfpdf->SetXY(15,$fila);//Numero fila 104
    $sfpdf->Write(5,utf8_decode('SEDE INSULAR DEL ITC EN ' .$provg.''));
    $sfpdf->SetFont('Arial','',11);
    $fila = $fila + 7;//Item12
    if($fila >= 203){
        $fila = 20;
    }
    $sfpdf->SetXY(15,$fila);
    
    $sfpdf->SetFont('Arial','B',9);
    //$sfpdf->SetTextColor(255,255,255);
    $sfpdf->Write(5,utf8_decode(
    'Se trata del edificio que alberga las oficinas principales del ITC en la provincia de Santa Cruz de Tenerife. Dispone de dos plantas con una superficie aproximada de unos'));
    $fila = $fila + 5;//Item13
    if($fila >= 203){
        $fila = 20;
    }
    $sfpdf->SetXY(15,$fila);//Numero fila 116m2, que albergan
    $sfpdf->Write(5,utf8_decode(
    '4.699 m2,  que albergan dependencias destinadas a oficinas administrativas,  despachos de la Dirección,  almacenes,  archivos,  CPD,  laboratorios,  nido para empresas,'));
    $fila = $fila + 5;//Item14
    if($fila >= 203){
        $fila = 20;
    }
    $sfpdf->SetXY(15,$fila);//Numero fila 121
    $sfpdf->Write(5,utf8_decode(
        'Salón de Actos, Salas de reuniones, Aulas de formación, Cafetería.'));
    $fila = $fila + 5;//Item15
    if($fila >= 203){
        $fila = 20;
    }
    $sfpdf->SetXY(15,$fila);//Numero fila 153
    $sfpdf->Write(5,utf8_decode(
    'El edificio dispone de un acceso principal situado en la 1ª Fase, un acceso auziliar en la 2ª fase y un acceso de servicio para tareas de carga/descarga.'));
    $fila = $fila + 5;//Item20
    if($fila >= 203){
        $fila = 20;
    }
    
    $sfpdf->SetXY(15,$fila);//Numero fila 163
    $sfpdf->Write(5,utf8_decode('El recinto que alberga el edificio se encuenta vallado y dispone de una puerta con motor para el acceso de vehículos y una puerta de paso para peatones.'));
    $fila = $fila + 5;//Item22
    if($fila >= 203){
        $fila = 20;
    }
    $sfpdf->SetXY(15,$fila);//Numero fila 168
    $fila = $fila + 5;
    $sfpdf->Write(5,utf8_decode('El centro también tiene de espacio exteriores alrededor del cuerpo frontal de la edificación, con una via interior, espacios ajardinados y aparcamientos al aire libre'));
    //$fila = $fila + 5;//Item23
    if($fila >= 203){
        $fila = 20;
    }
   
    $sfpdf->SetXY(15,$fila);//Numero fila 183
    $sfpdf->Write(5,utf8_decode('La recepción del edificio y cuarto de servicio con el cuadro de control de llaves y almacén de material de oficina se ubican junto al acceso de la 1ª Fase.'));
    $fila = $fila + 10;//Item26
    if($fila >= 203){
        $fila = 20;
    }
    
    

    $sfpdf->SetXY(15,$fila);//Numero fila 148
    $sfpdf->SetFont('Arial','B',8);
    $sfpdf->Write(5,utf8_decode('La funciones :'));
    $sfpdf->SetFont('Arial','',11);
    $fila = $fila + 5;//Item19
    if($fila >= 203){
        $fila = 20;
    }
     
    $sfpdf->SetFont('Arial','BU',8);
    $sfpdf->SetXY(15,$fila);//Numero fila 194
    $sfpdf->Write(5,utf8_decode('Labores de recepción:'));
    $sfpdf->SetFont('Arial','',11);
    $fila = $fila + 5;//Item28
    if($fila >= 203){
        $fila = 20;
    }
    $sfpdf->SetXY(15,$fila);//Numero fila 199
    $sfpdf->SetFont('Arial','B',8);
    $sfpdf->Write(5,utf8_decode('El edificio se cataloga como "edificio de pública concurrencia" en virtud de la naturaleza de actividades que desarrolla el ITC.'));
    $fila = $fila + 5;//Item29
    if($fila >= 203){
        $fila = 20;
    }
    
    $sfpdf->SetXY(15,$fila);//Numero fila 209
    $sfpdf->SetFont('Arial','B',8);
    $sfpdf->Write(5,utf8_decode('El edificio contiene dependencias de la sede insular del ITC, como oficinas en régimen de alquiler con capacidad para una veintena de empresas alojadas.'));
    $fila = $fila + 5;//Item31
    if($fila >= 203){
        $fila = 20;
    }
    
    $sfpdf->SetXY(15,$fila);//Numero fila 219
    $sfpdf->Write(5,utf8_decode('En la recepción se ubica la centralita telefónica del centro, equipos generales básicos del centro tales como la centralita contra incendio.'));
    $fila = $fila + 5;//Item33
    if($fila >= 203){
        $fila = 20;
    }
    $sfpdf->SetXY(15,$fila);//Numero fila 224
    $sfpdf->Write(5,utf8_decode('Además se hace el control de paquetería, correpondencia, control de acceso de personal y/o visitantes, así ccomo la recepción de llamadas de teléfono'));
    $fila = $fila + 5;//Item34
    if($fila >= 203){
        $fila = 20;
    }
    
    $sfpdf->SetXY(15,$fila);//Numero fila 234
    $sfpdf->Write(5,utf8_decode('recibidas en la centralita. También se realiza la labor de monitorización de los demás puntos de acceso existentes en el edificio.'));
    $fila = $fila + 5;//Item36
    if($fila >= 203){
        $fila = 20;
    }
    $sfpdf->SetXY(15,$fila);//Numero fila 239
    $sfpdf->Write(5,utf8_decode(''));
    $fila = $fila + 7;//Item37
    if($fila >= 203){
        $fila = 20;
    }
    $sfpdf->SetXY(15, $fila);
    $sfpdf->SetTextColor(255,0,0);
    $sfpdf->SetFont('Arial', 'B', 6);
    $sfpdf->Write(5, utf8_decode('ENVERA EMPLEO, S.L.U.'));
    $fila = $fila + 3; 
    $sfpdf->SetXY(15, $fila);
    $sfpdf->SEtFont('Arial', 'B', 6);
    $sfpdf->Write(5, utf8_decode('C/Bahía de Pollensa nº 25. 28042-Madrid   CIF.:B-86363603. Tlf.: 91 746 20 81 Fax.: 91 747 71 45'));
    $fila = $fila + 3;
    $sfpdf->SetXY(15, $fila);
    $sfpdf->SEtFont('Arial', 'B', 6);
    $sfpdf->Write(5, utf8_decode('Inscrita en el Registro mercantil TOMO: 29.509. LIBRO: 0. FOLIO: 111. SECCIÓN 8. HOJA: M-531080'));
    $fila = $fila + 3;
    $sfpdf->SetXY(15, $fila);
    $sfpdf->SEtFont('Arial', 'B', 6);
    $sfpdf->Write(5,utf8_decode('CEE DE TENERIFE - C/Cruz Caridad nº 42. 38350-Tacoronte. Santa Cruz de Tenerife. Tlf.:922 56 00 37'));
    $sfpdf->SetDrawColor(155, 155, 155);
    $sfpdf->SetLineWidth(0.3);
    $sfpdf->Line(15,280,190,280);
    $sfpdf->SetXY(15,$fila);//Numero fila 239
    $sfpdf->Write(5,utf8_decode(''));
    $fila = $fila + 1;//Item37
    if($fila >= 203){
        $fila = 20;
    }
    //***************** Añadimos SEGUNDA página del documento ****************
    $sfpdf->AddPage('L','A4',0);
    $sfpdf->SetMargins(30, 0 , 30);
    $sfpdf->SetTextColor(255,255,255);
    $sfpdf->Image('img/mi-cabecera.jpg',20,5,120,23);

    $sfpdf->SetDrawColor(155, 155, 155);
    $sfpdf->SetLineWidth(0.5);
    $sfpdf->Line(15,35,190,35);
    $fila = 30;
    $sfpdf->SetXY(15,$fila);
    $sfpdf->SetFont('Arial','U',11);
    $sfpdf->Write(5,utf8_decode('Alcance de los servicios.'));
    $fila = $fila + 10;//Item
    if($fila >= 203){
        $fila = 20;
    }
    $sfpdf->setxy(15,$fila);//Numero Fila 50
    $sfpdf->SetFont('Arial','',11);
    $sfpdf->Write(5,utf8_decode('Las tareas a desarrollar por los auxiliares de Recepción son, de carácter enunciativo y no'));
    $fila = $fila + 5;//Item
    if($fila >= 203){
        $fila = 20;
    }
    $sfpdf->setxy(15,$fila);//Numero Fila 55
    $sfpdf->Write(5,utf8_decode('limitativo, y con ciertas variaciones en función de las características de cada centro,  son'));
    $fila = $fila + 5;//Item
    if($fila >= 203){
        $fila = 20;
    }
    $sfpdf->setxy(15,$fila);//Numero Fila 60
    $sfpdf->Write(5,utf8_decode('las siguientes:'));
    $fila = $fila + 5;//Item
    if($fila >= 203){
        $fila = 20;
    }
    $sfpdf->setxy(15,$fila);//Numero Fila 65
    $sfpdf->Write(5,utf8_decode('a) Apertura y cierra de puertas y ventanas al inicio y final de la jornada.'));
    $fila = $fila + 5;//Item
    if($fila >= 203){
        $fila = 20;
    }
    $sfpdf->setxy(15,$fila);//Numero Fila 70
    $sfpdf->Write(5,utf8_decode('b) Atención de la centralita telefónica, gestión de fax central y registro de los visitantes.'));
    $fila = $fila + 5;//Item
    if($fila >= 203){
        $fila = 20;
    }
    $sfpdf->setxy(15,$fila);//Numero Fila 75
    $sfpdf->Write(5,utf8_decode('c) Atención, guía y orientación a visitas, proveedores, clientes de ITC., empresas alojadas.'));
    $fila = $fila + 5;//Item
    if($fila >= 203){
        $fila = 20;
    }
    $sfpdf->setxy(15,$fila);//Numero Fila 80
    $sfpdf->Write(5,utf8_decode('d) Custodia del cuadro de llaves de servicio y control de entregas y devolución de las mismas'));
    $fila = $fila + 5;//Item
    if($fila >= 203){
        $fila = 20;
    }
    $sfpdf->setxy(15,$fila);//Numero Fila 85
    $sfpdf->Write(5,utf8_decode('por los usuarios des edificio.'));
    $fila = $fila + 5;//Item
    if($fila >= 203){
        $fila = 20;
    }
    $sfpdf->setxy(15,$fila);//Numero Fila 90
    $sfpdf->Write(5,utf8_decode('e) Gestión centralizada de las entradas de correspondencia y/o paquetería.'));
    $fila = $fila + 5;//Item
    if($fila >= 203){
        $fila = 20;
    }
    $sfpdf->setxy(15,$fila);//Numero Fila 95
    $sfpdf->Write(5,utf8_decode('f) Tareas propias de portería y recepción.'));
    $fila = $fila + 5;//Item
    if($fila >= 203){
        $fila = 20;
    }
    $sfpdf->setxy(15,$fila);//Numero Fila 100
    $sfpdf->Write(5,utf8_decode('g) Ocasionalmente, colaboración en eventos celebrados en el centro.'));
    $fila = $fila + 5;//Item
    if($fila >= 203){
        $fila = 20;
    }
    $sfpdf->setxy(15,$fila);//Numero Fila 105
    $sfpdf->Write(5,utf8_decode('h) Operación rutinaria de equipos generales básicos del edificio.'));
    $fila = $fila + 5;//Item
    if($fila >= 203){
        $fila = 20;
    }
    $sfpdf->setxy(15,$fila);//Numero Fila 110
    $sfpdf->Write(5,utf8_decode('i) Participar en la feceta de comunicación de incidencias y/o alarmas en el edificio.'));
    $fila = $fila + 5;//Item
    if($fila >= 203){
        $fila = 20;
    }
    $sfpdf->SetFont('Arial','U',11);
    $sfpdf->setxy(15,$fila);//Numero Fila 115 
    $sfpdf->Write(5,utf8_decode('Medios humanos.'));
    $fila = $fila + 15;//Item 
    if($fila >= 203){
        $fila = 20;
    }
    $sfpdf->SetFont('Arial','',11);
    $sfpdf->setxy(15,$fila);//Numero Fila 130
    $sfpdf->Write(5,utf8_decode('El personal que ha prestado servicio en el mes es.:'));
    $fila = $fila + 10;//Item 
    if($fila >= 203){
        $fila = 20;
    }
    $sfpdf->setxy(15,$fila);//Numero Fila 140
    $sfpdf->Write(5,utf8_decode('Auxiliares de recepción.:'));
    $fila = $fila + 5;//Item 
    if($fila >= 203){
        $fila = 20;
    }
    $sfpdf->setXY(25,$fila);//Numero Fila 145
    $kk = "SELECT * FROM Usuarios WHERE UsuCentro = '".$cen."' AND UsuTipo = 'U'";
    $rkk = mysqli_query($conn, $kk);
    while($lafila = mysqli_fetch_row($rkk)){
        $fila = $fila + 5;
        if($fila >= 203){
            $fila = 20;
        }
        $sfpdf->SetXY(20,$fila);
        $sfpdf->Write(5,utf8_decode('- '.$lafila[4].''));
        $sfpdf->Write(5,utf8_decode(' '.$lafila[5].''));
        $sfpdf->Write(5,utf8_decode(', '.$lafila[3].''));
    }
    $fila = $fila + 10;
    $sfpdf->SetXY(15,$fila);
    if($fila >= 203){
        $fila = 20;
    }
    $sfpdf->Write(5,utf8_decode('Supervisor/a del servicio:'));
    $fila = $fila + 5;
    $sfpdf->SetXY(15,$fila);
    if($fila >= 203){
        $fila = 20;
    }
    $sfpdf->setxy(25,$fila);//Numero Fila 160
    if($cen == 1){
        $sfpdf->Write(5,utf8_decode('- Juan Antonio Francisco Vargas.'));
    }
    if($cen == 2){
        $sfpdf->Write(5,utf8_decode('- María Dácil Melgar García.'));
    }
    $fila = $fila + 10;
    $sfpdf->SetXY(15,$fila);
    if($fila >= 203){
        $fila = 20;
    }
    $sfpdf->SetXY(15,$fila);//Numero Fila 165
    $sfpdf->Write(5,utf8_decode('Coordinador/a del servicio.'));
    $fila = $fila + 5;
    $sfpdf->SetXY(15,$fila);
    if($fila >= 203){
        $fila = 20;
    }
    $sfpdf->SetXY(25,$fila);//Numero Fila 170
    $sfpdf->Write(5,utf8_decode('- Juan José Barrera Darias.'));
    $fila = $fila + 10;
    $sfpdf->SetXY(15,$fila);
    if($fila >= 203){
        $fila = 20;
    }
    $sfpdf->SetXY(15,$fila);//Numero Fila 180
    $sfpdf->Write(5, utf8_decode('Horarios de la prestación del servicio.'));
    $fila = $fila + 5;
    $sfpdf->SetXY(15,$fila);
    if($fila >= 203){
        $fila = 20;
    }
    $sfpdf->SetXY(15,$fila);//Numero Fila 185
    $sfpdf->Cell(80,5,utf8_decode('TAREAS DE PORTERÍA'),1,1,'C',0);
    $fila = $fila + 5;
    $sfpdf->SetXY(15,$fila);
    if($fila >= 203){
        $fila = 20;
    }
    $sfpdf->SetXY(15,$fila);//Numero Fila 190
    $sfpdf->Cell(40,5,utf8_decode('Apertura del edificio'),1,1,'C',0);
    $sfpdf->SetXY(55,$fila);//Numero Fila 190
    $sfpdf->Cell(40,5,utf8_decode('Cierre del edificio'),1,1,'C',0);
    $fila = $fila + 5;
    $sfpdf->SetXY(15,$fila);
    if($fila >= 203){
        $fila = 20;
    }
    $sfpdf->SetXY(15,$fila);//Numero Fila 195
    $sfpdf->Cell(40,5,utf8_decode('7:00 h'),1,1,'C',0);
    $sfpdf->SetXY(55,$fila);//Numero Fila 195
    $sfpdf->Cell(40,5,utf8_decode('17:00 h'),1,1,'C',0);
    $fila = $fila + 5;
    $sfpdf->SetXY(15,$fila);
    if($fila >= 203){
        $fila = 20;
    }
    $sfpdf->SetXY(95,$fila);//Numero Fila 210
    $sfpdf->Cell(80,5,utf8_decode('SERVICIO DE RECEPCIÓN'),1,1,'C',0);
    $fila = $fila + 5;
    $sfpdf->SetXY(15,$fila);
    if($fila >= 203){
        $fila = 20;
    }
    $sfpdf->SetXY(95,$fila);//Numero Fila 215
    $sfpdf->Cell(40,5,utf8_decode('Turno de Mañana'),1,1,'C',0);
    $sfpdf->SetXY(135,$fila);//Numero Fila 215
    $sfpdf->Cell(40,5,utf8_decode('Turno de Tarde'),1,1,'C',0);
    $fila = $fila + 5;
    $sfpdf->SetXY(15,$fila);
    if($fila >= 203){
        $fila = 20;
    }
    $sfpdf->SetXY(95,$fila);//Numero Fila 220
    $sfpdf->Cell(40,5,utf8_decode('De 7:00 h a 12:00h'),1,1,'C',0);
   
    $sfpdf->SetXY(135,$fila);//Numero Fila 220
    $sfpdf->Cell(40,5,utf8_decode('De 12:00h a 17:00 h'),1,1,'C',0);
    
    $sfpdf->SetDrawColor(155, 155, 155);
    $sfpdf->SetLineWidth(0.3);
    $sfpdf->Line(15,280,190,280);
    //Tercera página del documento
    $sfpdf->AddPage('L','A4',0);
    $fila = 20;
    $sfpdf->SetMargins(30, 0 , 30);
    $sfpdf->Image('img/mi-cabecera.jpg',20,5,120,23);
    $sfpdf->SetDrawColor(155, 155, 155);
    $sfpdf->SetLineWidth(0.5);
    $sfpdf->Line(15,35,290,35);
    $sfpdf->SetXY(15,$fila);
    $sfpdf->Write(4,utf8_decode('Indicencias comunicadas mediante email.'));
    $fila = $fila+5;
    $sfpdf->SetXY(15,$fila);
    $sfpdf->SetFont('Arial','B',8);
    $sfpdf->Cell(25,5,utf8_decode('Fecha'),1,0,'L',false);
    $fila = $fila+5;
    $sfpdf->SetXY(15,$fila);
    $sfpdf->Cell(275,5,utf8_decode('Incidencia'),1,0,'L',false);
    $sfpdf->SetFont('Arial','',8);
    $tt = "SELECT * FROM Incidencias WHERE IncCentro = '" .$cen. "' AND IncFecha LIKE '" .$dmesano."'";
    $rt = mysqli_query($conn, $tt);
    while($esfila = mysqli_fetch_row($rt)){
        $fila = $fila + 4;
        $sfpdf->SetXY(15,$fila);
        //$columna1 = strval(utf8_decode("'.substr($esfila[2],6,2).'"/"'.substr($esfila[2],4,2).'"/"'.substr($esfila[2],0,4).'"));
        //$columna2 = strval(utf8_decode("'.$esfila[4].'"));
        //echo utf8_decode("'.substr($esfila[2],6,2).'"/"'.substr($esfila[2],4,2).'"/"'.substr($esfila[2],0,4).'");
        $sfpdf->Cell(25,5,utf8_decode(substr($esfila[2],6,2)."/".substr($esfila[2],4,2)."/".substr($esfila[2],0,4)),0,0,'L',false);
        //$sfpdf->Write(5,utf8_decode("'.substr($esfila[2],6,2).'"/"'.substr($esfila[2],4,2).'"/"'.substr($esfila[2],0,4).'"));
        $fila = $fila + 4;
        $sfpdf->SetXY(15,$fila);
        $sfpdf->Cell(275,5,utf8_decode(substr($esfila[4],0,220)),0,0,'L',false);
        //$sfpdf->Write(5,utf8_decode(''.$esfila[4].''));
        if($fila > 165){
            $fila = 20;
            $sfpdf->AddPage('L','A4',0);
            $sfpdf->SetMargins(30, 0 , 30);
            $sfpdf->Image('img/mi-cabecera.jpg',20,5,120,23);
            $sfpdf->SetDrawColor(155, 155, 155);
            $sfpdf->SetLineWidth(0.5);
            $sfpdf->Line(15,30,290,30);
            $sfpdf->SetXY(15,$fila);
            $sfpdf->Write(4,utf8_decode('Indicencias comunicadas mediante email.'));
            $fila = $fila+5;
            $sfpdf->SetXY(15,$fila);
            $sfpdf->SetFont('Arial','B',8);
            $sfpdf->Cell(25,5,utf8_decode('Fecha'),1,0,'L',false);
            $fila = $fila+5;
            $sfpdf->SetXY(15,$fila);
            $sfpdf->Cell(275,5,utf8_decode('Incidencia'),1,0,'L',false);
        }
    }
    //Tercera página del documento
    $sfpdf->AddPage('P','A4',0);
    $fila = 20;
    $sfpdf->SetMargins(30, 0 , 30);
    $sfpdf->Image('img/mi-cabecera.jpg',20,5,120,23);
    $sfpdf->SetDrawColor(155, 155, 155);
    $sfpdf->SetLineWidth(0.5);
    $sfpdf->SetLineWidth(0.5);
    $sfpdf->Line(15,35,190,35);
    $sfpdf->SetXY(15,$fila);
    //$sfpdf->Cell(50,5,utf8_decode($cen),0,0,'L',false);
    //$sfpdf->Cell(50,5,utf8_decode($dmesano),0,0,'L',false);
    //$fila = $fila+1;
    $tt = "SELECT * FROM Movadoj WHERE MovCentro = '" .$cen. "' AND MovFechaEntrada LIKE '" .$dmesano."' ";
    $rst = mysqli_query($conn, $tt);
    $totalvisitas = mysqli_num_rows($rst);
    //$sfpdf->Cell(50,5,utf8_decode($totalvisitas),0,0,'L',false);
    $fila = $fila+5;
    //if($rsttt = mysqli_query($conn,$rst)){
    //    $totalvisitas = mysqli_num_rows($rsttt);
    //}
    $sfpdf->SetXY(15, $fila);
    $sfpdf->Cell(40,5,utf8_decode('Total visitas recibidas en el mes de ' .$esmes. ' es de '.$totalvisitas.''),0,0,'L',false);
    $fila = $fila+5;
    $sfpdf->SetXY(15,$fila);
    $sfpdf->SetFont('Arial','B',11);
    $sfpdf->Cell(100,5,utf8_decode('Visita a.'),1,0,'L',false);
    $sfpdf->Cell(30,5,utf8_decode('Veces.'),1,0,'L',false);
    $sfpdf->Cell(40,5,utf8_decode('Porcentaje.'),1,0,'C',false);
    $sfpdf->SetFont('Arial','',11);
    
    $fila = $fila+5;
    $qq = "SELECT DISTINCT MovDestino FROM Movadoj WHERE MovCentro = '" .$cen. "' AND MovFechaEntrada LIKE '" .$dmesano."' ";
    $resultad2 = mysqli_query($conn, $qq);
    while($mostrar = mysqli_fetch_row($resultad2)){
        $fila = $fila + 5;
        $sfpdf->SetXY(15,$fila);
        $eldestino = $mostrar[0];
        $sfpdf->Write(5,utf8_decode($eldestino));
        //$sfpdf->Cell(50,5,utf8_decode($eldestino),0,0,'L',false);
        $vv = "SELECT * FROM Movadoj WHERE MovDestino = '".$eldestino."' AND MovFechaEntrada LIKE '".$dmesano."' ";
        $mivv = mysqli_query($conn,$vv);
        $veces = mysqli_num_rows($mivv);
        $sfpdf->SetXY(120,$fila);
        $sfpdf->Cell(30,5,utf8_decode($veces),0,0,'C',false);
        //$sfpdf->Write(5,utf8_decode($veces));
        //$sfpdf->Cell(50,5,utf8_decode($veces),0,0,'L',false);
        $elpeso = round(($veces / $totalvisitas *100),2);
        $sfpdf->SetXY(155,$fila);
        $sfpdf->Cell(30,5,utf8_decode($elpeso),0,0,'R',false);
        //$sfpdf->Write(5,utf8_decode(''.$elpeso.' %'));
        //$sfpdf->Cell(50,5,utf8_decode($elpeso),0,0,'L',false);
        //$fila = $fila + 5;
    }
    
    $sfpdf->Output($textotitulo,'D');
    ob_end_flush();
    
?>