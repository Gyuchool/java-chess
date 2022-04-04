package chess.dao;

import chess.Board;
import chess.Team;
import chess.Turn;
import chess.piece.Piece;
import chess.piece.PieceFactory;
import chess.piece.Pieces;
import chess.utils.JdbcConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BoardDaoImpl implements BoardDao {

    @Override
    public Optional<Turn> findTurnById(Long id) {
        final String query = "SELECT (turn) from board where id = ?";
        try (
                Connection connection = JdbcConnector.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                String turn = result.getString("turn");
                return Optional.of(new Turn(Team.from(turn)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void updateTurnById(Long id, String newTurn) {
        final String query = "UPDATE board set turn = ? where id = ?";
        try (
                Connection connection = JdbcConnector.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.setString(1, newTurn);
            statement.setLong(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Long save() {
        final String query = "INSERT INTO board (turn) values (?)";
        try (
                Connection connection = JdbcConnector.getConnection();
                PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, "white");
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if(resultSet.next()){
                return resultSet.getLong(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalStateException("[ERROR] 디비 저장에 실패했습니다.");
    }

    @Override
    public Optional<Board> findById(Long id) {
        final String query = "SELECT (b.turn, p.position, p.type, p.team) " +
                "FROM board b " +
                "JOIN piece p ON b.id = p.id " +
                "WHERE b.id = ?";
        try (
                Connection connection = JdbcConnector.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            List<Piece> pieces = new ArrayList<>();
            String turn ="";
            while(resultSet.next()){
                turn = resultSet.getString(1);
                String position = resultSet.getString(2);
                String type = resultSet.getString(3);
                String team = resultSet.getString(4);
                pieces.add(PieceFactory.create(position, team, type));
            }
            return Optional.of(Board.create(Pieces.from(pieces), new Turn(Team.from(turn))));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
