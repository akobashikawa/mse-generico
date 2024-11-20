import { ref, useTemplateRef, onMounted, watch, nextTick } from 'vue';
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

	<alfa-component ref="alfa" :api-url="alfaApiUrl"/>
	
	<beta-component ref="beta" :api-url="betaApiUrl"/>

	<gamma-component ref="gamma" :api-url="gammaApiUrl" 
	:alfa-api-url="alfaApiUrl"  
	:beta-api-url="betaApiUrl"
	@item-created="onGammaItemCreated" />

	<footer>
		<em><a href="https://github.com/akobashikawa/mse-generico" target="_blank">@GitHub></a></em>
	</footer>
    `,

	setup() {
		const apiUrl = config.apiUrl;

		const alfaApiUrl = `${apiUrl}/api/alfa`;
		const betaApiUrl = `${apiUrl}/api/beta`;
		const gammaApiUrl = `${apiUrl}/api/gamma`;

		const alfaRef = useTemplateRef('alfa');
		const betaRef = useTemplateRef('beta');
		// const gammaRef = useTemplateRef('gamma');

		const onGammaItemCreated = () => {
			alfaRef.value?.getItems();
			betaRef.value?.getItems();
		};

		onMounted(() => {
		});

		return {
			alfaApiUrl,
			betaApiUrl,
			gammaApiUrl,
			onGammaItemCreated,
		}
	},

};

export default App;