import React, { useState } from 'react'; // Importamos o useState para salvar o que o usuário digita
import { 
  StyleSheet, 
  Text, 
  View, 
  TextInput, 
  TouchableOpacity, 
  Image, 
  StatusBar, 
  KeyboardAvoidingView, 
  Platform, 
  TouchableWithoutFeedback, 
  Keyboard,
  Alert // Importamos o Alert nativo para mostrar mensagens na tela
} from 'react-native';

export default function App() {
  // Criamos "estados" (variáveis especiais) para guardar o texto do e-mail e da senha
  const [email, setEmail] = useState('');
  const [senha, setSenha] = useState('');

  // Função que é executada quando o usuário clica no botão "Entrar"
  const handleLogin = () => {
    // Validação simples: verifica se os campos não estão vazios
    if (email.trim() === '' || senha.trim() === '') {
      Alert.alert('Atenção', 'Por favor, preencha todos os campos.');
      return;
    }

    // Se estiver tudo preenchido, exibe a caixinha de sucesso!
    Alert.alert(
      'Sucesso', 
      `Login realizado com sucesso!\n\nUsuário: ${email}`,
      [{ text: 'OK' }] // Botão para fechar o alerta
    );
  };

  return (
    // TouchableWithoutFeedback monitora os cliques na tela. 
    // O Keyboard.dismiss faz o teclado fechar se você clicar no fundo azul.
    <TouchableWithoutFeedback onPress={Keyboard.dismiss}>
      
      {/* KeyboardAvoidingView é o responsável pela responsividade. 
          Ele empurra os campos para cima quando o teclado do celular abre. */}
      <KeyboardAvoidingView 
        behavior={Platform.OS === 'ios' ? 'padding' : 'height'} 
        style={styles.container}
      >
        {/* StatusBar controla a barra de status superior do celular (hora, bateria, etc) */}
        <StatusBar barStyle="light-content" backgroundColor="#0A1D25" />

        {/* Área do Logotipo extraído do seu design do Figma */}
        <View style={styles.logoContainer}>
          <Image 
            source={require('./assets/Logo - Redesenho vetorial.png')} 
            style={styles.logo} 
          />
        </View>

        {/* Formulário que agrupa os campos de acesso */}
        <View style={styles.formContainer}>
          
          {/* Campo de Entrada de E-mail */}
          <View style={styles.inputGroup}>
            <Text style={styles.label}>E-mail</Text>
            <TextInput 
              style={styles.input} 
              placeholder="Digite seu e-mail" 
              placeholderTextColor="rgba(255, 255, 255, 0.4)"
              keyboardType="email-address" // Otimiza o teclado para digitar e-mail (mostra o '@')
              autoCapitalize="none" // Impede o celular de colocar a primeira letra em maiúscula automaticamente
              value={email} // Vincula o texto ao nosso estado
              onChangeText={setEmail} // Atualiza o estado toda vez que o usuário digita uma letra
            />
          </View>

          {/* Campo de Entrada de Senha */}
          <View style={styles.inputGroup}>
            <Text style={styles.label}>Senha</Text>
            <TextInput 
              style={styles.input} 
              placeholder="Digite sua senha" 
              placeholderTextColor="rgba(255, 255, 255, 0.4)"
              secureTextEntry={true} // Esconde os caracteres digitados com pontinhos por segurança
              value={senha} // Vincula o texto ao nosso estado
              onChangeText={setSenha} // Atualiza o estado com a senha digitada
            />
          </View>

          {/* Botão customizável de Entrar */}
          <TouchableOpacity 
            style={styles.button} 
            activeOpacity={0.8} // Controla o efeito de opacidade ao clicar no botão
            onPress={handleLogin} //Chama a função handleLogin para disparar o alerta
          >
            <Text style={styles.buttonText}>Entrar</Text>
          </TouchableOpacity>

        </View>
      </KeyboardAvoidingView>
    </TouchableWithoutFeedback>
  );
}

// Estilização visual (CSS do React Native)
const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#0A1D25', // Cor Azul Petróleo Escuro Corporativo
    justifyContent: 'center',
    paddingHorizontal: 30,
  },
  logoContainer: {
    alignItems: 'center',
    marginBottom: 40,
  },
  logo: {
    width: 130,
    height: 130,
    resizeMode: 'contain', // Garante que a imagem do logo não fique distorcida
  },
  formContainer: {
    width: '100%',
  },
  inputGroup: {
    marginBottom: 20,
  },
  label: {
    color: '#FFF',
    fontSize: 14,
    marginBottom: 8,
    fontWeight: '500',
  },
  input: {
    backgroundColor: 'rgba(255, 255, 255, 0.06)', // Fundo cinza bem transparente
    color: '#FFF',
    paddingVertical: 14,
    paddingHorizontal: 16,
    borderRadius: 8,
    fontSize: 16,
    borderWidth: 1,
    borderColor: 'rgba(255, 255, 255, 0.12)', // Borda sutil para dar acabamento
  },
  button: {
    backgroundColor: '#FFF', // Botão branco contrastando com o fundo escuro
    paddingVertical: 15,
    borderRadius: 8,
    alignItems: 'center',
    marginTop: 15,
  },
  buttonText: {
    color: '#0A1D25', // Texto do botão na mesma cor do fundo
    fontSize: 16,
    fontWeight: 'bold',
  },
});