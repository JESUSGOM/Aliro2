<?php
session_name("Aliros");
session_start();
ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);
require_once("db.php");
require_once("include/header.php");
require_once("variables.php");
require 'include/user_sesion.php';
$idmenu = 0;

// Conectar a la base de datos con manejo de errores
$conn = mysqli_connect('mysql-8001.dinaserver.com', 'Conacelbs', 'Pass@LIr0S', 'Conlabac');

if (!$conn) {
    die("Error de conexión: " . mysqli_connect_error());
}

mysqli_set_charset($conn, "utf8");


// Verificar que las variables de sesión estén definidas antes de usarlas
$apellidoUno = isset($_SESSION["apellidoUno"]) ? $_SESSION["apellidoUno"] : "";
$apellidoDos = isset($_SESSION["apellidoDos"]) ? $_SESSION["apellidoDos"] : "";
$nombre = isset($_SESSION["nombre"]) ? $_SESSION["nombre"] : "";
$centroDen = isset($_SESSION["centre"]) ? $_SESSION["centre"] : "";
$centro = isset($_SESSION["centro"]) ? $_SESSION["centro"] : "";
$identifico = isset($_SESSION["usuario"]) ? $_SESSION["usuario"] : "";
$rolusuario = isset($_SESSION["tipo"]) ? $_SESSION["tipo"] : "";


// Obtener datos del menú principal
$mimenu = "SELECT MnNombre, MnUrl, MnId, MnSvg, MnParentId
               FROM Menus, MapaMenu
               WHERE MmUsuTipo = '$rolusuario' 
               AND MmCentro = '$centro'
               AND MnId = MmMnId
               AND MnParentId = 0
               ORDER BY MnId"; //Añadido filtro MnParentId

$resultmenu = mysqli_query($conn, $mimenu);


if (!$resultmenu) {
    die("Error en la consulta del menú principal: " . mysqli_error($conn));
}
$listamenus = [];
if($resultmenu->num_rows > 0) {
    while($row = $resultmenu->fetch_assoc()) {
        $listamenus[] = $row;
    }
}

$misubmenu = "SELECT MnNombre, MnUrl, MnSvg
                    FROM Menus, MapaMenu
                    WHERE MnParentId = $idmenu
                    AND MmUsuTipo = '$rolusuario'
                    AND MmCentro = '$centro'
                    AND MnId = MmMnId
                    AND MnParentId <> 0
                    ORDER BY MnId";

$resultsubmenu = mysqli_query($conn, $misubmenu);
if (!$resultsubmenu) {
    die("Error en la consulta del submenú: " . mysqli_error($conn));
}
$listasubmenus = [];
if($resultsubmenu->num_rows > 0) {
    while($row = $resultsubmenu->fetch_assoc()) {
        $listasubmenus[] = $row;
    }
}
?>

<!doctype html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css">
</head>
<body>
<div class="container-fluid" id="contenedor">
    <div class="row">
        <div class="col">
            <div class="center-block">
                <p>


                    <?php
                    $espacio = " ";
                    $coma = ", ";

                    $estaidentificacion = "**" . substr($identifico, 2, 2) . "*" . substr($identifico, 5, 1) . "**" . substr($identifico, 8, 1);


                    echo "<br> \n";
                    echo "<b>";
                    echo $espacio;
                    echo $centroDen; //Usar variable previamente definida
                    echo $espacio;
                    echo "<b>Usuario:  -> </b>";
                    echo "</b>";
                    echo $espacio;
                    echo $estaidentificacion; //Usar variable previamente definida

                    echo $espacio;
                    echo $apellidoUno;
                    echo $espacio;
                    echo $apellidoDos;
                    echo $coma;
                    echo $nombre;
                    echo $espacio;
                    echo $rolusuario;
                    echo $espacio;
                    echo $centro;


                    ?>
                </p>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col">
            <div class="center-block">
                <nav class="navbar navbar-expand-lg navbar-light bg-light">
                    <a class="navbar-brand" href="#">Navbar</a>
                    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                        <span class="navbar-toggler-icon"></span>
                    </button>
                    <div class="collapse navbar-collapse" id="navbarNav">
                        <ul class="navbar-nav">
                            <?php foreach ($listamenus as $menu): ?>
                                <li class="nav-item dropdown">
                                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                        <img src="<?php echo $menu['MnSvg']; ?>" alt="<?php echo $menu['MnNombre']; ?>" style="height: 24px;">
                                    </a>
                                    <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                                        <?php foreach ($listasubmenus as $submenu): ?>
                                        <a class="dropdown-item" href="#"><?php echo $submenu['MnNombre']; ?> 1</a>
                                        <a class="dropdown-item" href="#"><?php echo $submenu['MnNombre']; ?> 2</a>
                                        <div class="dropdown-divider"></div>
                                        <a class="dropdown-item" href="#"><?php echo $submenu['MnNombre']; ?> 3</a>
                                    </div>
                                    <?php endforeach; ?>
                                </li>
                            <?php endforeach; ?>
                        </ul>
                    </div>
                </nav>


            </div>
        </div>
    </div>


    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>