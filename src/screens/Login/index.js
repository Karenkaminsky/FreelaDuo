// src/screens/Login/index.js
import React, { useState, useEffect } from 'react';
import { 
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
  Alert,
  ScrollView 
} from 'react-native';
import * as NavigationBar from 'expo-navigation-bar';
import { Feather, MaterialCommunityIcons, AntDesign } from '@expo/vector-icons';

// IMPORTAMOS O NOSSO ARQUIVO DE ESTILOS SEPARADO AQUI:
import { styles } from './styles'; 

export default function LoginScreen() {
  const [email, setEmail] = useState('');
  const [senha, setSenha] = useState('');
  const [perfil, setPerfil] = useState('freelancer'); 

  useEffect(() => {
    if (Platform.OS === 'android') {
      NavigationBar.setBackgroundColorAsync('#000000');
      NavigationBar.setButtonStyleAsync('light');
    }
  }, []);

  const handleLogin = () => {
    if (email.trim() === '' || senha.trim() === '') {
      Alert.alert('Atenção', 'Por favor, preencha todos os campos.');
      return;
    }
    Alert.alert('Sucesso', `Login realizado como ${perfil}!\n\nUsuário: ${email}`);
  };

  const handleForgotPassword = () => {
    Alert.alert('Recuperar Senha', 'Uma mensagem de recuperação será enviada.');
  };

  const handleSocialLogin = (platform) => {
    Alert.alert('Login Social', `Conectando com o ${platform}...`);
  };

  const handleNavigateToRegister = () => {
    Alert.alert('Cadastro', 'Redirecionando para a tela de criação de conta...');
  };

  return (
    <TouchableWithoutFeedback onPress={Keyboard.dismiss}>
      <KeyboardAvoidingView 
        behavior={Platform.OS === 'ios' ? 'padding' : 'height'} 
        style={styles.container}
      >
        <StatusBar barStyle="light-content" backgroundColor="#000000" />

        <ScrollView 
          contentContainerStyle={styles.scrollContent}
          showsVerticalScrollIndicator={false}
          keyboardShouldPersistTaps="handled"
        >

          {/* Logo do FreelaDuo */}
          <View style={styles.logoContainer}>
            <Image 
              source={require('../../../assets/Logo - Redesenho vetorial.png')} 
              style={styles.logo} 
            />
          </View>

          {/* Textos de Boas-Vindas */}
          <View style={styles.welcomeContainer}>
            <Text style={styles.welcomeTitle}>Bem-vindo!</Text>
            <Text style={styles.welcomeSubtitle}>
              Acesse sua conta e continue conectando talentos a oportunidades.
            </Text>
          </View>

          {/* Seletores de Perfil */}
          <View style={styles.profileSelectorContainer}>
            <TouchableOpacity 
              style={[styles.profileCard, perfil === 'freelancer' && styles.profileCardSelected]}
              onPress={() => setPerfil('freelancer')}
              activeOpacity={0.9}
            >
              <View style={[styles.iconCircle, perfil === 'freelancer' && styles.iconCircleSelected]}>
                <Feather name="user" size={22} color={perfil === 'freelancer' ? '#FFF' : '#A3A3A3'} />
              </View>
              <Text style={[styles.profileCardTitle, perfil === 'freelancer' && styles.textSelected]}>
                Sou freelancer
              </Text>
              <Text style={styles.profileCardSubtitle}>Encontre projetos e faça seu negócio</Text>
              <View style={styles.checkContainer}>
                <MaterialCommunityIcons 
                  name={perfil === 'freelancer' ? "checkbox-marked-circle" : "checkbox-blank-circle-outline"} 
                  size={18} 
                  color={perfil === 'freelancer' ? '#8A3FFC' : 'rgba(255,255,255,0.2)'} 
                />
              </View>
            </TouchableOpacity>

            <TouchableOpacity 
              style={[styles.profileCard, perfil === 'empresa' && styles.profileCardSelected]}
              onPress={() => setPerfil('empresa')}
              activeOpacity={0.9}
            >
              <View style={[styles.iconCircle, perfil === 'empresa' && styles.iconCircleSelected]}>
                <Feather name="home" size={22} color={perfil === 'empresa' ? '#FFF' : '#A3A3A3'} />
              </View>
              <Text style={[styles.profileCardTitle, perfil === 'empresa' && styles.textSelected]}>
                Sou empresa
              </Text>
              <Text style={styles.profileCardSubtitle}>Contrate talentos e acelere projetos</Text>
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

            <TouchableOpacity 
              style={styles.forgotPasswordContainer} 
              onPress={handleForgotPassword}
              activeOpacity={0.7}
            >
              <Text style={styles.forgotPasswordText}>Esqueceu a senha?</Text>
            </TouchableOpacity>

            <TouchableOpacity style={styles.button} activeOpacity={0.8} onPress={handleLogin}>
              <Text style={styles.buttonText}>Entrar</Text>
            </TouchableOpacity>
          </View>

          {/* Linha Divisória */}
          <View style={styles.dividerContainer}>
            <View style={styles.dividerLine} />
            <Text style={styles.dividerText}>ou continue com</Text>
            <View style={styles.dividerLine} />
          </View>

          {/* Botões de Redes Sociais */}
          <View style={styles.socialButtonsContainer}>
            <TouchableOpacity style={styles.socialButton} onPress={() => handleSocialLogin('Google')} activeOpacity={0.7}>
              <AntDesign name="google" size={20} color="#EA4335" />
              <Text style={styles.socialButtonText}>Google</Text>
            </TouchableOpacity>

            <TouchableOpacity style={styles.socialButton} onPress={() => handleSocialLogin('LinkedIn')} activeOpacity={0.7}>
              <AntDesign name="linkedin-square" size={20} color="#0A66C2" />
              <Text style={styles.socialButtonText}>LinkedIn</Text>
            </TouchableOpacity>

            <TouchableOpacity style={styles.socialButton} onPress={() => handleSocialLogin('GitHub')} activeOpacity={0.7}>
              <AntDesign name="github" size={20} color="#FFF" />
              <Text style={styles.socialButtonText}>GitHub</Text>
            </TouchableOpacity>
          </View>

          {/* Texto de Cadastro Inferior */}
          <TouchableOpacity style={styles.registerContainer} onPress={handleNavigateToRegister} activeOpacity={0.7}>
            <Text style={styles.registerNormalText}>
              Ainda não tem uma conta? <Text style={styles.registerBoldText}>Criar conta</Text>
            </Text>
          </TouchableOpacity>

        </ScrollView>
      </KeyboardAvoidingView>
    </TouchableWithoutFeedback>
  );
}