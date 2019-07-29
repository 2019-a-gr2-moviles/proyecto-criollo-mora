/**
 * Perro.js
 *
 * @description :: A model definition represents a database table/collection.
 * @docs        :: https://sailsjs.com/docs/concepts/models-and-orm/models
 */

module.exports = {

  attributes: {

    sexo: {
      type: 'string',
      required: true,
      minLength: 1,
      maxLength: 20,
    },
    edad: {
      type: 'string',
      required: true,
      minLength: 3,
      maxLength: 30,
    },
    color: {
      type: 'string',
      required: true,
      minLength: 3,
      maxLength: 30,
    },


    //  CONFIGURACION DEL HIJO
    idRaza: {  // Nombre del FK para la relacion
      model: 'razaPerro', // Nombre del modelo a relacionar (papa)
      required: true // OPCIONAL -> Siempre ingresar el FK
    },

    idPersona: {  // Nombre del FK para la relacion
      model: 'persona', // Nombre del modelo a relacionar (papa)
      //required: true // OPCIONAL -> Siempre ingresar el FK
    },

    idTienda: {  // Nombre del FK para la relacion
      model: 'tiendaMascotas', // Nombre del modelo a relacionar (papa)
      //required: true // OPCIONAL -> Siempre ingresar el FK
    }/*,

    //Configuracion del papa
    perroXPersona: { // Nombre atributo de la relacion
      collection: 'perroPorPersona', //Nombre del modelo a relacionar
      via: 'idPerroXPersona' //Nombre atributo FK del otro modelo
    },

    //Configuracion del papa
    perroXTienda: { // Nombre atributo de la relacion
      collection: 'perroPorTienda', //Nombre del modelo a relacionar
      via: 'idPerroXTienda' //Nombre atributo FK del otro modelo
    }

*/
  },

};


