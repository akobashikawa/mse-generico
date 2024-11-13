import { ref, onMounted, watch, nextTick } from 'vue';
import axios from 'axios';
import config from '../config.js';
import AlfaComponent from './AlfaComponent.js';
import BetaComponent from './BetaComponent.js';

const App = {

	components: {
		AlfaComponent,
		BetaComponent,
	},

	template: `
	<h1>HTTP</h1>

	<alfa-component :api-url="alfaApiUrl"/>
	
	<beta-component :api-url="betaApiUrl"/>

	<footer>
		<em><a href="https://github.com/akobashikawa/mse-generico" target="_blank">@GitHub></a></em>
	</footer>
    `,

	setup() {
		// Obtener la URL base de configuración
		const apiUrl = config.apiUrl;

		const alfaApiUrl = `${apiUrl}/api/alfa`;
		const betaApiUrl = `${apiUrl}/api/beta`;

		return {
			alfaApiUrl,
			betaApiUrl,
		}
	},

};

export default App;