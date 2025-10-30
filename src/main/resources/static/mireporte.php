<?php
    require ("fpdf/fpdf.php");
    $fdpf = new fpdf();
    $sfpdf->AddPage('L','A4',0);
    
    class pdf extends FPDF {
        public function header(){
            $this->SetFont('Arial', 'B', 12);
            $this->Write(5, 'Centro Educativo Colonia La Paz');

        }

        public function footer(){
            $this->SetFont('Arial','B',6);
            $this->SetY(-15);
            $this->Write(5, 'San Miguel, El Salvador');
        }
    }

    $fpdf = new pdf();
    $fpdf->AddPage('L', 'A4');
    $fpdf->SetFont('Arial', '', 14);
    $fpdf->Cell(0, 5, 'Listado de estudiantes matriculados.');
    $fpdf->AddPage();
    $textotitulo = 'Fichero de prueba.pdf';
    $sfpdf->Output($textotitulo,'D');
    ob_end_flush();

?>