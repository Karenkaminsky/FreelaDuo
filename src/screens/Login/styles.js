// src/screens/Login/styles.js
import { StyleSheet } from 'react-native';

export const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#000000',
  },
  scrollContent: {
    paddingHorizontal: 24,
    paddingTop: 50, 
    paddingBottom: 30, 
  },

  //Muda a distancia entre a logo com o texto
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
    flexDirection: 'row',
    justifyContent: 'space-between',
    marginBottom: 25,
  },
  profileCard: {
    width: '48%',
    backgroundColor: 'rgba(255, 255, 255, 0.03)',
    borderRadius: 16,
    padding: 16,
    borderWidth: 1.5,
    borderColor: 'rgba(255, 255, 255, 0.08)',
    position: 'relative',
  },
  profileCardSelected: {
    backgroundColor: 'rgba(138, 63, 252, 0.08)',
    borderColor: '#8A3FFC',
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
    backgroundColor: '#8A3FFC',
  },
  profileCardTitle: {
    color: '#A3A3A3',
    fontSize: 15,
    fontWeight: '600',
    marginBottom: 4,
  },
  textSelected: {
    color: '#FFF',
  },
  profileCardSubtitle: {
    color: '#737373',
    fontSize: 12,
    lineHeight: 16,
    marginBottom: 15,
  },
  checkContainer: {
    position: 'absolute',
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
  dividerContainer: {
    flexDirection: 'row',
    alignItems: 'center',
    marginVertical: 25, 
  },
  dividerLine: {
    flex: 1,
    height: 1,
    backgroundColor: 'rgba(255, 255, 255, 0.12)', 
  },
  dividerText: {
    color: '#737373',
    paddingHorizontal: 12,
    fontSize: 14,
  },
  socialButtonsContainer: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    marginBottom: 35,
  },
  socialButton: {
    width: '31%', 
    flexDirection: 'row',
    backgroundColor: 'rgba(255, 255, 255, 0.03)',
    borderWidth: 1,
    borderColor: 'rgba(255, 255, 255, 0.08)',
    borderRadius: 10,
    paddingVertical: 12,
    alignItems: 'center',
    justifyContent: 'center',
  },
  socialButtonText: {
    color: '#FFF',
    fontSize: 13,
    fontWeight: '500',
    marginLeft: 6, 
  },
  registerContainer: {
    alignItems: 'center',
    marginTop: 10,
    marginBottom: 15,
  },
  registerNormalText: {
    color: '#A3A3A3',
    fontSize: 14,
  },
  registerBoldText: {
    color: '#8A3FFC', 
    fontWeight: 'bold',
  },
});