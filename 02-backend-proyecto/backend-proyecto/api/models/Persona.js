/**
 * Persona.js
 *
 * @description :: A model definition represents a database table/collection.
 * @docs        :: https://sailsjs.com/docs/concepts/models-and-orm/models
 */

module.exports = {

  attributes: {

    nombre: {
      type: 'string',
      required: true,
      minLength: 3,
      maxLength: 30,
    },
    apellido: {
      type: 'string',
      required: true,
      minLength: 3,
      maxLength: 30,
    },
    cedula: {
      type: 'string',
      required: true,
      minLength: 10,
      maxLength: 15,
    },
    fechaNac: {
      type: 'string',
      required: true,
      minLength: 5,
      maxLength: 20,
    }//,

    /*
    //  CONFIGURACION DEL HIJO
    fkEmpresa: {  // Nombre del FK para la relacion
      model: 'empresa', // Nombre del modelo a relacionar (papa)
      // required: true // OPCIONAL -> Siempre ingresar el FK
    },*/
    /*
        //Configuracion del papa
        serviciosDeUsuario: { // Nombre atributo de la relacion
          collection: 'servicio', //Nombre del modelo a relacionar
          via: 'fkUsuario' //Nombre atributo FK del otro modelo
        }*/


  },

};


