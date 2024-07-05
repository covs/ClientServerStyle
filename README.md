Descripción del caso de uso

Este código simula una transacción de remesa desde el exterior, donde el cliente representa la entidad que envía la remesa y el servidor representa el sistema bancario que la recibe y procesa. Los detalles de la remesa, se ingresan de manera dinámica por consola, haciendo el sistema más flexible y adaptable a diferentes escenarios.

Cliente: El cliente solicita la cuenta y el monto de la remesa por consola, envía un mensaje de remesa en el formato remesa:<número de cuenta>:<monto> al servidor, recibe y muestra la respuesta del servidor.
Servidor:El servidor escucha por mensajes que empiezan con "remesa:" y procesa la remesa, valida la remesa mediante el método procesarRemesa y responde con un mensaje de éxito o error.
Main:Solicita la IP y el puerto del servidor y los datos de la remesa del cliente por consola, inicia el servidor en un hilo separado y da tiempo para que el servidor arranque, inicia el cliente, envía los datos de la remesa y muestra la respuesta del servidor.
