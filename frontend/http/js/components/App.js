import { ref, onMounted, watch, nextTick } from 'vue';
import axios from 'axios';
import config from '../config.js';

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
	template: `
	<h1>HTTP</h1>
			<div class="flash danger error-messages" ref="errorContainer" v-if="errorMessages.length > 0">
				<div class="error-message-header">
					<a href="#" @click.prevent="deleteErrorMessages()">[clear]</a>
				</div>
				<ul>
					<li v-for="(errorMessage, index) in errorMessages" :key="index">
						<a href="#" @click.prevent="deleteErrorMessage(index)">[x]</a>
						{{ errorMessage }}
					</li>
				</ul>
			</div>

			<h2>Alfa</h2>

			<button @click="getAlfaItems()">Traer items</button>
			<button @click="openIngresarAlfaItemDialog">Ingresar Item</button>

			<table>
				<thead>
					<tr>
						<th>id</th>
						<th>texto</th>
						<th>entero</th>
						<th>decimal</th>
						<th>acciones</th>
					</tr>
				</thead>
				<tbody>
					<tr v-for="item of alfaItems">
						<td>{{ item.id }}</td>
						<td>{{ item.texto }}</td>
						<td>{{ item.entero }}</td>
						<td>{{ item.decimal }}</td>
						<td>
							<button @click="openModificarAlfaItemDialog(alfaItem.id)">Modificar</button>
						</td>
					</tr>
				</tbody>
			</table>

			<dialog ref="ingresarAlfaItemDialog">
				<header>
					<h2>Ingresar Item</h2>
				</header>
				<form method="dialog">
					<legend>Nuevo item</legend>
					<fieldset>
						<label> Texto: <input type="text" placeholder="" v-model="alfaItem.texto"> </label> 
						<label> Entero: <input type="number" step="1" min="0" placeholder="1" v-model="alfaItem.entero"> </label>
						<label> Decimal: <input type="number" step="0.10" min="0" placeholder="0.00" v-model="alfaItem.decimal"> </label> 
					</fieldset>
					<footer>
						<button type="reset" @click="closeIngresarAlfaItemDialog">Cancelar</button>
						<button type="submit" @click="createAlfaItem">Guardar</button>
					</footer>

				</form>
			</dialog>

			<dialog ref="modificarAlfaItemDialog">
				<header>
					<h2>Modificar Item</h2>
				</header>
				<form method="dialog">
					<fieldset>
						<legend>Item</legend>
						<label> ID: {{ alfaItem.id }} <input type="hidden" placeholder="" v-model="alfaItem.id"> </label> 
						<label> Texto: <input type="text" placeholder="" v-model="alfaItem.texto"> </label> 
						<label> Entero: <input type="number" step="1" min="0" placeholder="1" v-model="alfaItem.entero"> </label>
						<label> Decimal: <input type="number" step="0.10" min="0" placeholder="0.00" v-model="alfaItem.decimal"> </label>
					</fieldset>
					<footer>
						<button type="reset" @click="closeModificarAlfaItemDialog">Cancelar</button>
						<button type="submit" @click="updateAlfaItem">Guardar</button>
					</footer>

				</form>
			</dialog>

			<footer>
				<em><a href="https://github.com/akobashikawa/mse-generico" target="_blank">@GitHub></a></em>
			</footer>
    `,

	setup() {
		// UTILS
		const errorMessages = ref([]);
		const errorContainer = ref(null);

		const deleteErrorMessage = (index) => {
			errorMessages.value.splice(index, 1);
		};

		const deleteErrorMessages = (index) => {
			errorMessages.value = [];
		};

		const formatDate = (date) => {
			const d = new Date(date);
			const year = d.getFullYear();
			const month = String(d.getMonth() + 1).padStart(2, '0');
			const day = String(d.getDate()).padStart(2, '0');
			const hours = String(d.getHours()).padStart(2, '0');
			const minutes = String(d.getMinutes()).padStart(2, '0');

			return `${year}/${month}/${day} ${hours}:${minutes}`;
		};

		// LISTAR ALFA ITEMS
		let alfaItems = ref([]);

		const getAlfaItems = async () => {
			const { data } = await axios.get(alfaServiceUrl);
			if (typeof data != 'object') {
				alfaItems.value = [];
				errorMessages.value.push('alfaItems: data invalid');
				return;
			}
			alfaItems.value = data;
		};

		const getAlfaItem = async (id) => {
			const { data } = await axios.get(`${alfaServiceUrl}/${id}`);
			alfaItem.value = data;
		};

		// const selectAlfaItem = () => {
		// 	const alfa_id = beta.value.alfa_id;
		// 	const found = alfa.value.find(item => alfa.id === alfa_id);
		// 	if (found) {
		// 		beta.value.decimal = found.decimal;
		// 	}
		// };

		// CREAR ALFA ITEM
		const alfaItem = ref({
			id: 0,
			texto: '',
			entero: 0,
			decimal: 0,
		});

		const ingresarAlfaItemDialog = ref();
		const openIngresarAlfaItemDialog = () => {
			ingresarAlfaItemDialog.value.showModal();
		};
		const closeIngresarAlfaItemDialog = () => {
			ingresarAlfaItemDialog.value.close();
		};

		const createAlfaItem = async () => {
			const body = alfaItem.value;
			const { data } = await axios.post(alfaServiceUrl, body);
			// await getAlfaItems();
		};

		// MODIFICAR ALFA ITEM
		const modificarAlfaItemDialog = ref();
		const openModificarAlfaItemDialog = async (id) => {
			await getAlfaItem(id);
			modificarAlfaItemDialog.value.showModal();
		};
		const closeModificarAlfaItemDialog = () => {
			modificarAlfaItemDialog.value.close();
		};

		const updateAlfaItem = async () => {
			const body = alfaItem.value;
			const { data } = await axios.put(`${alfaServiceUrl}/${body.id}`, body);
			// await getAlfaItems();
		};

		onMounted(async () => {
			// await getAlfaItems();
		});

		watch(
			errorMessages,
			() => {
				// Al cambiar errorMessages, desplazar al final
				nextTick(() => {
					if (errorContainer.value) {
						errorContainer.value.scrollTop = errorContainer.value.scrollHeight;
					}
				});
			},
			{ deep: true }
		);

		return {
			errorMessages,
			errorContainer,
			deleteErrorMessage,
			deleteErrorMessages,
			formatDate,

			alfaItem,
			alfaItems,
			getAlfaItems,
			// selectAlfaItem,
			ingresarAlfaItemDialog,
			openIngresarAlfaItemDialog,
			closeIngresarAlfaItemDialog,
			createAlfaItem,
			modificarAlfaItemDialog,
			openModificarAlfaItemDialog,
			closeModificarAlfaItemDialog,
			updateAlfaItem,

		}
	},

};

export default App;