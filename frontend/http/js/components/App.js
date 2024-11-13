import { ref, onMounted, watch, nextTick } from 'vue';
import axios from 'axios';
import config from '../config.js';
import AlfaComponent from './AlfaComponent.js';
import BetaComponent from './BetaComponent.js';
import GammaComponent from './GammaComponent.js';

const App = {

	components: {
		AlfaComponent,
		BetaComponent,
		GammaComponent,
	},

	template: `
	<h1>HTTP</h1>

	<alfa-component :api-url="alfaApiUrl"/>
	
	<beta-component :api-url="betaApiUrl"/>

	<gamma-component :api-url="gammaApiUrl" 
	:alfa-api-url="alfaApiUrl"  
	:beta-api-url="betaApiUrl"/>

	<footer>
		<em><a href="https://github.com/akobashikawa/mse-generico" target="_blank">@GitHub></a></em>
	</footer>
    `,

	setup() {
		// Obtener la URL base de configuración
		const apiUrl = config.apiUrl;

		const alfaApiUrl = `${apiUrl}/api/alfa`;
		const betaApiUrl = `${apiUrl}/api/beta`;
		const gammaApiUrl = `${apiUrl}/api/gamma`;

		return {
			alfaApiUrl,
			betaApiUrl,
			gammaApiUrl,
		}
	},

};

export default App;