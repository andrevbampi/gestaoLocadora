package controller;

import java.sql.SQLException;
import java.util.List;

import controller.dao.ReservaDao;
import controller.dao.UsuarioDao;
import controller.dao.impl.ReservaDaoImpl;
import controller.dao.impl.UsuarioDaoImpl;
import controller.exception.BusinessRuleException;
import controller.util.ControllerUtils;
import model.Usuario;

public class UsuarioController {
	
    private UsuarioDao usuarioDao;
    private ReservaDao reservaDao;
    private EnderecoController enderecoController;
    private Usuario usuarioSistema;

    public UsuarioController(Usuario usuarioSistema) {
        usuarioDao = new UsuarioDaoImpl();
        reservaDao = new ReservaDaoImpl();
        enderecoController = new EnderecoController();
        this.usuarioSistema = usuarioSistema;
    }

    private void validarUsuario(Usuario usuario, String confirmaSenha, boolean estaEditando) throws BusinessRuleException {
    	if (!usuarioSistema.isAdministrador() && usuario.getId() != usuarioSistema.getId()) {
    		throw new BusinessRuleException("Usuário não administrador pode apenas editar o próprio usuário.");
    	}
    	
    	if (usuario.getLogin().trim().equals("")) {
    		throw new BusinessRuleException("Login não informado.");
    	}
    	
    	if (!estaEditando) {
	    	if (usuario.getSenha().trim().equals(ControllerUtils.gerarHashMD5(""))) {
	    		throw new BusinessRuleException("Senha não informada.");
	    	}
	    	if (confirmaSenha.trim().equals(ControllerUtils.gerarHashMD5(""))) {
	    		throw new BusinessRuleException("Repita a senha.");
	    	}
    	}
    	
    	if (usuario.getNome().trim().equals("")) {
    		throw new BusinessRuleException("Nome não informado.");
    	}
    	
    	if (usuario.getGenero() == null) {
    		throw new BusinessRuleException("Gênero não informado.");
    	}
    	
    	if (usuario.getTelefone().trim().equals("")) {
    		throw new BusinessRuleException("Telefone não informado.");
    	}
    	
        if (!usuario.getSenha().equals(confirmaSenha)) {
        	throw new BusinessRuleException("Senhas não conferem.");
        }
        
        if (!usuarioSistema.isAdministrador() && usuario.isAdministrador()) {
        	throw new BusinessRuleException("Usuário não administrador não pode acrescentar privilégio de administrador ao próprio usuário.");
        }
        
        try {
            if (usuarioDao.verificarLogin(usuario.getLogin(), usuario.getId())) {
            	throw new BusinessRuleException("Já existe um usuário com esse login no sistema.");
            }
        } catch (SQLException ex) {
        	throw new BusinessRuleException("Falha técnica ao selecionar usuário no sistema. Consulte o suporte técnico. \nDetalhes técnicos: " + ex.getMessage());
        }
    }

    public Usuario efetuarLogin(Usuario usuario) throws BusinessRuleException {
		try {
			Usuario usuarioVerif = usuarioDao.verificarUsuario(usuario);
			if (usuarioVerif == null) {
	    		throw new BusinessRuleException("Usuário ou senha não conferem.");
	    	}
			if (!usuarioVerif.isAtivo()) {
				throw new BusinessRuleException("Usuário inativo.");
			}
	    	return usuarioVerif;
		} catch (SQLException ex) {
			throw new BusinessRuleException("Falha técnica ao efetuar login no sistema. Consulte o suporte técnico. \nDetalhes técnicos: " + ex.getMessage());
		}
    }

    public Usuario salvar(Usuario usuario, String confirmarSenha) throws BusinessRuleException {
        try {
            validarUsuario(usuario, confirmarSenha, false);
            usuario.setEndereco(enderecoController.salvar(usuario.getEndereco()));
            return usuarioDao.salvar(usuario);
        } catch (SQLException ex) {
        	excluirEndereco(usuario.getEndereco().getId());
        	throw new BusinessRuleException("Falha técnica ao cadastrar usuário no sistema. Consulte o suporte técnico. \nDetalhes técnicos: " + ex.getMessage());
        }
    }

    public void editar(Usuario usuario, String confirmarSenha) throws BusinessRuleException {
    	try {
    		validarUsuario(usuario, confirmarSenha, true);
    		int idEnderecoAnterior = usuarioDao.buscarPorId(usuario.getId()).getEndereco().getId();
    		usuario.setEndereco(enderecoController.salvar(usuario.getEndereco()));
	        usuarioDao.editar(usuario);
	        excluirEndereco(idEnderecoAnterior);
    	} catch (SQLException ex) {
    		excluirEndereco(usuario.getEndereco().getId());
        	throw new BusinessRuleException("Falha técnica ao editar usuário no sistema. Consulte o suporte técnico. \nDetalhes técnicos: " + ex.getMessage());
        }
    }
    
    public Usuario buscarPorId(int id) throws BusinessRuleException {
    	if (!usuarioSistema.isAdministrador() && id != usuarioSistema.getId()) {
    		throw new BusinessRuleException("Usuário não administrador não pode visualizar dados de outro usuário.");
    	}
    	try {
			Usuario usuario = usuarioDao.buscarPorId(id);
			if (usuario == null) {
				throw new BusinessRuleException("Não existe usuário cadastrado com esse id.");
			}
			usuario.setEndereco(enderecoController.buscarPorId(usuario.getEndereco().getId()));
	    	return usuario;
		} catch (SQLException ex) {
			throw new BusinessRuleException("Falha técnica ao buscar usuário por id no sistema. Consulte o suporte técnico. \nDetalhes técnicos: " + ex.getMessage());
		}
    }
    
    public Usuario buscarPorLogin(String login) throws BusinessRuleException {
    	if (!usuarioSistema.isAdministrador() && login.trim() != usuarioSistema.getLogin().trim()) {
    		throw new BusinessRuleException("Usuário não administrador não pode visualizar dados de outro usuário.");
    	}
    	try {
			Usuario usuario = usuarioDao.buscarPorLogin(login);
			if (usuario == null) {
				throw new BusinessRuleException("Não existe usuário cadastrado com esse login.");
			}
			usuario.setEndereco(enderecoController.buscarPorId(usuario.getEndereco().getId()));
	    	return usuario;
		} catch (SQLException ex) {
			throw new BusinessRuleException("Falha técnica ao buscar usuário por login no sistema. Consulte o suporte técnico. \nDetalhes técnicos: " + ex.getMessage());
		}
    }
    
    public void excluir(int id) throws BusinessRuleException {
    	if (!usuarioSistema.isAdministrador()) {
    		throw new BusinessRuleException("Usuário não administrador não pode excluir usuários.");
    	}
    	try {
    		if (reservaDao.getQtdeReservasPorCliente(id) > 0) {
    			throw new BusinessRuleException("Não é possível excluir esse usuário porque ele possui reserva de veículo no sistema.");
    		}
    		Usuario usuario = buscarPorId(id);
			usuarioDao.excluir(usuario.getId());
			excluirEndereco(usuario.getEndereco().getId());
    	} catch (SQLException ex) {
			throw new BusinessRuleException("Falha técnica ao excluir usuário no sistema. Consulte o suporte técnico. \nDetalhes técnicos: " + ex.getMessage());
		}
    }
    
    private void excluirEndereco(int idEndereco) throws BusinessRuleException {
    	try {
	    	int qtd = usuarioDao.getQtdUsuariosMesmoIdEndereco(idEndereco);
			if (qtd == 0) {
				enderecoController.excluir(idEndereco);
			}
    	} catch (SQLException ex) {
			throw new BusinessRuleException("Falha técnica ao verificar endereço de usuário no sistema. Consulte o suporte técnico. \nDetalhes técnicos: " + ex.getMessage());
		}
    }
    
    public List<Usuario> listarUsuarios(Usuario usuario, boolean checarAdministrador, boolean checarAtivo) throws BusinessRuleException {
    	try {
    		List<Usuario> listaUsuariosRet = usuarioDao.listarUsuarios(usuario, checarAdministrador, checarAtivo);
			if (!usuarioSistema.isAdministrador()) {
				for (Usuario usuarioLista : listaUsuariosRet) {
					if (usuarioLista.getId() != usuarioSistema.getId()) {
						throw new BusinessRuleException("Usuário não administrador não pode visualizar dados de outro usuário.");
					}
				}
			}
			return listaUsuariosRet;
		} catch (SQLException ex) {
			throw new BusinessRuleException("Falha técnica ao listar usuários no sistema. Consulte o suporte técnico. \nDetalhes técnicos: " + ex.getMessage());
		}
    }
}
