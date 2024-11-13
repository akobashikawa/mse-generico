import { ref, onMounted, watch, nextTick } from 'vue';
import axios from 'axios';
import config from '../config.js';
import AlfaComponent from './AlfaComponent.js';

const App = {

	components: {
		AlfaComponent,
	},

	template: `
	<h1>HTTP</h1>

	<alfa-component :api-url="alfaApiUrl"/>

	<footer>
		<em><a href="https://github.com/akobashikawa/mse-generico" target="_blank">@GitHub></a></em>
	</footer>
    `,

	setup() {
		// Obtener la URL base de configuración
		const apiUrl = config.apiUrl;

		const alfaApiUrl = `${apiUrl}/api/alfa`;

		return {
			alfaApiUrl,
		}
	},

};

export default App;