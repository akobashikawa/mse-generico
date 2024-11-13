import { ref, onMounted, watch, nextTick } from 'vue';
import axios from 'axios';
import config from '../config.js';
import AlfaComponent from './AlfaComponent.js';

// Obtener la URL base de la página actual
const baseUrl = config.apiBaseUrl;

// Construir las URLs de los servicios
const alfaServiceUrl = `${baseUrl}/api/alfa`;
const betaServiceUrl = `${baseUrl}/api/beta`;
const gammaServiceUrl = `${baseUrl}/api/gamma`;

// Puedes usar estas URLs para tus peticiones, por ejemplo:
console.log('Alfa URL:', alfaServiceUrl);
console.log('Beta URL:', betaServiceUrl);
console.log('Gamma URL:', gammaServiceUrl);

const App = {

	components: {
		AlfaComponent,
	},

	template: `
	<h1>HTTP</h1>

	<alfa-component :api-url="alfaServiceUrl"/>

	<footer>
		<em><a href="https://github.com/akobashikawa/mse-generico" target="_blank">@GitHub></a></em>
	</footer>
    `,

	setup() {
		const alfaServiceUrl = `${baseUrl}/api/alfa`;

		return {
			alfaServiceUrl,
		}
	},

};

export default App;