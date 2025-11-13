<?php
    require_once("db.php");
    require_once("variables.php");
    session_start();
    require 'include/user_sesion.php';
    require ("fpdf/fpdf.php");
    define('FPDF_FONTPATH', 'font/');
    class PDF extends FPDF {
        //Header
        function Header(){
            $this->Image('img/logoitcizq.png', 5, 5, 50);
            $this->SetFont('Arial','B',15);
            $this->SetTextColor(255,0,0);
            $this->Cell(80);
            $enveratenerife = utf8_decode("ENVERA EMPLEO, S.L.U.
    C/Bahía de Pollensa nº25. 28042-Madrid  CIF.:B-86363603 Tlf:917 462 081  Fax:917 477 145 
    Inscrita en el Registro Mercantil TOMO: 29.509, LIBRO: 0, FOLIO: 111; SECCIÓN: 8, HOJA: M-531080
    CEE DE TENERIFE C/Cruz Caridad nº42. 38350-Tacoronte. Tenerife. Tlf.:922 560 037");
            $this->MultiCell(30,10,$enveratenerife,0,0,'L');
            //$this->CELL(30,10,$enveratenerife,1,0,'C');
            $this->Ln(20);
        }
        //Footer
        function Footer(){
            $this->SetY(-15);
            $this->setFont('Arial','B',6);
            $this->Cell(0,10,'Página. '.$this->PageNo().'/{nb}',0,0,'C');
        }
    }
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
    $pdf = new PDF();
    $pdf->AliasNbPages();
    $pdf->AddPage('L','A4',0);
    $pdf->SetFont('Times','',12);
    for($i=1;$i<40;$i++){
        $pdf->Cell(0,10,'Número de línea: '.$i,0,1);
        $pdf->Cell(50,10,'Nuber of line:' .$i,0,0);
    }
    $pdf->Output();


?>