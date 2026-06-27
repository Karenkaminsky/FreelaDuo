import React, { useState, useEffect } from 'react';
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
  Alert 
} from 'react-native';
import * as NavigationBar from 'expo-navigation-bar';
import { Feather, MaterialCommunityIcons } from '@expo/vector-icons'; // Ícones nativos do Expo (User, Home e Check)

export default function App() {
  const [email, setEmail] = useState('');
  const [senha, setSenha] = useState('');
  
  // Estado para controlar o perfil ativo: começa como 'freelancer' por padrão
  const [perfil, setPerfil] = useState('freelancer'); 

  // Configuração da barra inferior do Android
  useEffect(() => {
    if (Platform.OS === 'android') {
      NavigationBar.setBackgroundColorAsync('#000000');
      NavigationBar.setButtonStyleAsync('light');
    }
  }, []);

  // Disparado ao tentar logar
  const handleLogin = () => {
    if (email.trim() === '' || senha.trim() === '') {
      Alert.alert('Atenção', 'Por favor, preencha todos os campos.');
      return;
    }

    // Alerta customizado mostrando qual tipo de perfil está logando
    Alert.alert(
      'Sucesso', 
      `Login realizado como ${perfil === 'freelancer' ? 'Freelancer' : 'Empresa'}!\n\nUsuário: ${email}`,
      [{ text: 'OK' }]
    );
  };

  const handleForgotPassword = () => {
    Alert.alert('Recuperar Senha', 'Uma mensagem de recuperação será enviada para o seu e-mail.');
  };

  return (
    <TouchableWithoutFeedback onPress={Keyboard.dismiss}>
      <KeyboardAvoidingView 
        behavior={Platform.OS === 'ios' ? 'padding' : 'height'} 
        style={styles.container}
      >
        <StatusBar barStyle="light-content" backgroundColor="#000000" />

        {/* Logo do FreelaDuo */}
        <View style={styles.logoContainer}>
          <Image 
            source={require('./assets/Logo - Redesenho vetorial.png')} 
            style={styles.logo} 
          />
        </View>

        {/* Textos de Boas-Vindas baseados no design (image_9c8f5f.png) */}
        <View style={styles.welcomeContainer}>
          <Text style={styles.welcomeTitle}>Bem-vindo de volta! 👋</Text>
          <Text style={styles.welcomeSubtitle}>
            Acesse sua conta e continue conectando talentos a oportunidades.
          </Text>
        </View>

        {/* Bloco dos Seletores de Perfil (Freelancer / Empresa) */}
        <View style={styles.profileSelectorContainer}>
          
          {/* Card: Sou Freelancer */}
          <TouchableOpacity 
            // Aplica o estilo padrão e adiciona a borda/fundo roxo apenas se estiver selecionado
            style={[
              styles.profileCard, 
              perfil === 'freelancer' && styles.profileCardSelected
            ]}
            onPress={() => setPerfil('freelancer')} // Muda o estado para 'freelancer' ao clicar
            activeOpacity={0.9}
          >
            {/* Círculo do Ícone: ganha fundo roxo se selecionado */}
            <View style={[styles.iconCircle, perfil === 'freelancer' && styles.iconCircleSelected]}>
              <Feather name="user" size={22} color={perfil === 'freelancer' ? '#FFF' : '#A3A3A3'} />
            </View>
            
            {/* Título do Card */}
            <Text style={[styles.profileCardTitle, perfil === 'freelancer' && styles.textSelected]}>
              Sou freelancer
            </Text>
            
            {/* Descrição do Card */}
            <Text style={styles.profileCardSubtitle}>Encontre projetos e faça seu negócio</Text>
            
            {/* Bolinha do Check (canto inferior direito) */}
            <View style={styles.checkContainer}>
              <MaterialCommunityIcons 
                name={perfil === 'freelancer' ? "checkbox-marked-circle" : "checkbox-blank-circle-outline"} 
                size={18} 
                color={perfil === 'freelancer' ? '#8A3FFC' : 'rgba(255,255,255,0.2)'} 
              />
            </View>
          </TouchableOpacity>

          {/* Card: Sou Empresa */}
          <TouchableOpacity 
            style={[
              styles.profileCard, 
              perfil === 'empresa' && styles.profileCardSelected
            ]}
            onPress={() => setPerfil('empresa')} // Muda o estado para 'empresa' ao clicar
            activeOpacity={0.9}
          >
            {/* Círculo do Ícone */}
            <View style={[styles.iconCircle, perfil === 'empresa' && styles.iconCircleSelected]}>
              <Feather name="home" size={22} color={perfil === 'empresa' ? '#FFF' : '#A3A3A3'} />
            </View>
            
            {/* Título do Card */}
            <Text style={[styles.profileCardTitle, perfil === 'empresa' && styles.textSelected]}>
              Sou empresa
            </Text>
            
            {/* Descrição do Card */}
            <Text style={styles.profileCardSubtitle}>Contrate talentos e acelere projetos</Text>
            
            {/* Bolinha do Check */}
            <View style={styles.checkContainer}>
              <MaterialCommunityIcons 
                name={perfil === 'empresa' ? "checkbox-marked-circle" : "checkbox-blank-circle-outline"} 
                size={18} 
                color={perfil === 'empresa' ? '#8A3FFC' : 'rgba(255,255,255,0.2)'} 
              />
            </View>
          </TouchableOpacity>

        </View>

        {/* Formulário de Login */}
        <View style={styles.formContainer}>
          
          <View style={styles.inputGroup}>
            <Text style={styles.label}>E-mail</Text>
            <TextInput 
              style={styles.input} 
              placeholder="Digite seu e-mail" 
              placeholderTextColor="rgba(255, 255, 255, 0.4)"
              keyboardType="email-address"
              autoCapitalize="none"
              value={email}
              onChangeText={setEmail}
            />
          </View>

          <View style={styles.inputGroup}>
            <Text style={styles.label}>Senha</Text>
            <TextInput 
              style={styles.input} 
              placeholder="Digite sua senha" 
              placeholderTextColor="rgba(255, 255, 255, 0.4)"
              secureTextEntry={true}
              value={senha}
              onChangeText={setSenha}
            />
          </View>

          {/* Botão Esqueceu a Senha */}
          <TouchableOpacity 
            style={styles.forgotPasswordContainer} 
            onPress={handleForgotPassword}
            activeOpacity={0.7}
          >
            <Text style={styles.forgotPasswordText}>Esqueceu a senha?</Text>
          </TouchableOpacity>

          {/* Botão Entrar */}
          <TouchableOpacity 
            style={styles.button} 
            activeOpacity={0.8} 
            onPress={handleLogin}
          >
            <Text style={styles.buttonText}>Entrar</Text>
          </TouchableOpacity>
        </View>
      </KeyboardAvoidingView>
    </TouchableWithoutFeedback>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#000000',
    justifyContent: 'center',
    paddingHorizontal: 24,
  },
  logoContainer: {
    alignItems: 'center',
    marginBottom: 20,
  },
  logo: {
    width: 130,
    height: 40, 
    resizeMode: 'contain',
  },
  welcomeContainer: {
    alignItems: 'center',
    marginBottom: 25,
  },
  welcomeTitle: {
    color: '#FFF',
    fontSize: 22,
    fontWeight: 'bold',
    marginBottom: 6,
    textAlign: 'center',
  },
  welcomeSubtitle: {
    color: '#A3A3A3',
    fontSize: 14,
    textAlign: 'center',
    paddingHorizontal: 20,
    lineHeight: 20,
  },
  profileSelectorContainer: {
    flexDirection: 'row', // Organiza os dois cards um do lado do outro (em linha)
    justifyContent: 'space-between',
    marginBottom: 25,
  },
  profileCard: {
    width: '48%', // Faz cada card ocupar quase metade da linha, deixando um espaçamento sutil entre eles
    backgroundColor: 'rgba(255, 255, 255, 0.03)', // Fundo cinza quase invisível (tema escuro)
    borderRadius: 16,
    padding: 16,
    borderWidth: 1.5,
    borderColor: 'rgba(255, 255, 255, 0.08)',
    position: 'relative', // Permite o posicionamento absoluto do ícone de Check interno
  },
  profileCardSelected: {
    backgroundColor: 'rgba(138, 63, 252, 0.08)', // Fundo roxo bem transparente para destaque
    borderColor: '#8A3FFC', // Borda roxa oficial da marca
  },
  iconCircle: {
    width: 40,
    height: 40,
    borderRadius: 20,
    backgroundColor: 'rgba(255,255,255,0.06)',
    justifyContent: 'center',
    alignItems: 'center',
    marginBottom: 12,
  },
  iconCircleSelected: {
    backgroundColor: '#8A3FFC', // Círculo fica roxo quando selecionado
  },
  profileCardTitle: {
    color: '#A3A3A3',
    fontSize: 15,
    fontWeight: '600',
    marginBottom: 4,
  },
  textSelected: {
    color: '#FFF', // Texto fica aceso (branco) se o card estiver ativo
  },
  profileCardSubtitle: {
    color: '#737373',
    fontSize: 12,
    lineHeight: 16,
    marginBottom: 15, // Espaço extra em baixo para o ícone de check não cobrir o texto
  },
  checkContainer: {
    position: 'absolute', // Prende a bolinha de check de forma fixa dentro do card
    bottom: 12,
    right: 12,
  },
  formContainer: {
    width: '100%',
  },
  inputGroup: {
    marginBottom: 16,
  },
  label: {
    color: '#FFF',
    fontSize: 14,
    marginBottom: 8,
    fontWeight: '500',
  },
  input: {
    backgroundColor: 'rgba(255, 255, 255, 0.05)', 
    color: '#FFF',
    paddingVertical: 12,
    paddingHorizontal: 16,
    borderRadius: 8,
    fontSize: 16,
    borderWidth: 1,
    borderColor: 'rgba(255, 255, 255, 0.1)',
  },
  forgotPasswordContainer: {
    alignSelf: 'flex-end',
    marginBottom: 20,
  },
  forgotPasswordText: {
    color: '#007AFF',
    fontSize: 14,
    fontWeight: '500',
  },
  button: {
    backgroundColor: '#FFF', 
    paddingVertical: 14,
    borderRadius: 8,
    alignItems: 'center',
  },
  buttonText: {
    color: '#000000', 
    fontSize: 16,
    fontWeight: 'bold',
  },
});